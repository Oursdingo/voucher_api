package com.octopus.voucher.controller;

import com.octopus.voucher.dto.request.AgenceCreateRequest;
import com.octopus.voucher.dto.request.AgenceUpdateRequest;
import com.octopus.voucher.dto.response.AgenceResponse;
import com.octopus.voucher.service.AgenceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

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

@WebMvcTest(AgenceController.class)
class AgenceControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AgenceService agenceService;

    @Test
    void shouldReturnAllAgences() throws Exception {
        AgenceResponse response1 = AgenceResponse.builder().id(UUID.randomUUID()).nom("Agence A").build();
        AgenceResponse response2 = AgenceResponse.builder().id(UUID.randomUUID()).nom("Agence B").build();

        when(agenceService.getAll()).thenReturn(List.of(response1, response2));

        mockMvc.perform(get("/api/v1/agences"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].nom").value("Agence A"))
                .andExpect(jsonPath("$[1].nom").value("Agence B"));
    }

    @Test
    void shouldReturnAgenceById() throws Exception {
        UUID id = UUID.randomUUID();
        AgenceResponse response = AgenceResponse.builder().id(id).nom("Agence C").code("AGC-001").build();

        when(agenceService.getById(id)).thenReturn(response);

        mockMvc.perform(get("/api/v1/agences/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("Agence C"))
                .andExpect(jsonPath("$.code").value("AGC-001"));
    }

    @Test
    void shouldCreateAgence() throws Exception {
        AgenceResponse response = AgenceResponse.builder()
                .id(UUID.randomUUID())
                .nom("Agence D")
                .code("AGD-001")
                .build();

        when(agenceService.create(any(AgenceCreateRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/agences")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nom": "Agence D",
                                  "code": "AGD-001",
                                  "regionalId": "%s",
                                  "pointVenteIds": ["%s"]
                                }""".formatted(UUID.randomUUID(), UUID.randomUUID())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nom").value("Agence D"))
                .andExpect(jsonPath("$.code").value("AGD-001"));

        verify(agenceService).create(any(AgenceCreateRequest.class));
    }

    @Test
    void shouldUpdateAgence() throws Exception {
        UUID id = UUID.randomUUID();
        AgenceResponse response = AgenceResponse.builder()
                .id(id)
                .nom("Agence E")
                .code("AGE-001")
                .build();

        when(agenceService.update(any(AgenceUpdateRequest.class))).thenReturn(response);

        mockMvc.perform(put("/api/v1/agences")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "id": "%s",
                                  "nom": "Agence E"
                                }""".formatted(id)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("Agence E"));

        verify(agenceService).update(any(AgenceUpdateRequest.class));
    }

    @Test
    void shouldDeleteAgence() throws Exception {
        UUID id = UUID.randomUUID();
        doNothing().when(agenceService).delete(id);

        mockMvc.perform(delete("/api/v1/agences/{id}", id))
                .andExpect(status().isNoContent());

        verify(agenceService).delete(id);
    }

    @Test
    void shouldRejectCreateWhenPointVenteIdsEmpty() throws Exception {
        mockMvc.perform(post("/api/v1/agences")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nom": "Agence F",
                                  "code": "AGF-001",
                                  "pointVenteIds": []
                                }"""))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(agenceService);
    }

    @Test
    void shouldRejectUpdateWhenOnlyIdProvided() throws Exception {
        UUID id = UUID.randomUUID();
        mockMvc.perform(put("/api/v1/agences")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "id": "%s"
                                }""".formatted(id)))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(agenceService);
    }
}
