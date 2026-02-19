package com.octopus.voucher.controller;

import com.octopus.voucher.dto.request.EventCreateRequest;
import com.octopus.voucher.dto.request.EventUpdateRequest;
import com.octopus.voucher.dto.response.EventResponse;
import com.octopus.voucher.enumeration.ActionEnum;
import com.octopus.voucher.service.EventService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EventController.class)
class EventControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EventService eventService;

    @Test
    void shouldReturnAllEvents() throws Exception {
        EventResponse response1 = EventResponse.builder().id(UUID.randomUUID()).entite("User").build();
        EventResponse response2 = EventResponse.builder().id(UUID.randomUUID()).entite("Voucher").build();

        when(eventService.getAll()).thenReturn(List.of(response1, response2));

        mockMvc.perform(get("/api/v1/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].entite").value("User"))
                .andExpect(jsonPath("$[1].entite").value("Voucher"));
    }

    @Test
    void shouldReturnEventById() throws Exception {
        UUID id = UUID.randomUUID();
        EventResponse response = EventResponse.builder()
                .id(id)
                .actionEnum(ActionEnum.CONNEXION)
                .entite("User")
                .build();

        when(eventService.getById(id)).thenReturn(response);

        mockMvc.perform(get("/api/v1/events/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.actionEnum").value("CONNEXION"))
                .andExpect(jsonPath("$.entite").value("User"));
    }

    @Test
    void shouldCreateEvent() throws Exception {
        EventResponse response = EventResponse.builder()
                .id(UUID.randomUUID())
                .actionEnum(ActionEnum.CONNEXION)
                .entite("User")
                .build();

        when(eventService.create(any(EventCreateRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "actionEnum": "CONNEXION",
                                  "entite": "User",
                                  "date": "%s",
                                  "adresse": "127.0.0.1",
                                  "userId": "%s"
                                }""".formatted(LocalDateTime.now().minusMinutes(5), UUID.randomUUID())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.actionEnum").value("CONNEXION"));

        verify(eventService).create(any(EventCreateRequest.class));
    }

    @Test
    void shouldUpdateEvent() throws Exception {
        UUID id = UUID.randomUUID();
        EventResponse response = EventResponse.builder()
                .id(id)
                .actionEnum(ActionEnum.DECONNEXION)
                .build();

        when(eventService.update(any(EventUpdateRequest.class))).thenReturn(response);

        mockMvc.perform(put("/api/v1/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "id": "%s",
                                  "actionEnum": "DECONNEXION"
                                }""".formatted(id)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.actionEnum").value("DECONNEXION"));

        verify(eventService).update(any(EventUpdateRequest.class));
    }

    @Test
    void shouldDeleteEvent() throws Exception {
        UUID id = UUID.randomUUID();
        doNothing().when(eventService).delete(id);

        mockMvc.perform(delete("/api/v1/events/{id}", id))
                .andExpect(status().isNoContent());

        verify(eventService).delete(id);
    }

    @Test
    void shouldRejectCreateWhenActionMissing() throws Exception {
        mockMvc.perform(post("/api/v1/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "entite": "User",
                                  "date": "%s",
                                  "adresse": "127.0.0.1",
                                  "userId": "%s"
                                }""".formatted(LocalDateTime.now().minusMinutes(5), UUID.randomUUID())))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(eventService);
    }

    @Test
    void shouldRejectUpdateWhenOnlyIdProvided() throws Exception {
        UUID id = UUID.randomUUID();
        mockMvc.perform(put("/api/v1/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "id": "%s"
                                }""".formatted(id)))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(eventService);
    }
}
