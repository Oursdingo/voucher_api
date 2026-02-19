package com.octopus.voucher.controller;

import com.octopus.voucher.dto.request.RegionalCreateRequest;
import com.octopus.voucher.dto.request.RegionalUpdateRequest;
import com.octopus.voucher.dto.response.RegionalResponse;
import com.octopus.voucher.enumeration.DregionalEnum;
import com.octopus.voucher.service.RegionalService;
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

@WebMvcTest(RegionalController.class)
class RegionalControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RegionalService regionalService;

    @Test
    void shouldReturnAllRegionals() throws Exception {
        RegionalResponse response1 = RegionalResponse.builder().id(UUID.randomUUID()).nom("Centre").build();
        RegionalResponse response2 = RegionalResponse.builder().id(UUID.randomUUID()).nom("Est").build();

        when(regionalService.getAll()).thenReturn(List.of(response1, response2));

        mockMvc.perform(get("/api/v1/regionals"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].nom").value("Centre"))
                .andExpect(jsonPath("$[1].nom").value("Est"));
    }

    @Test
    void shouldReturnRegionalById() throws Exception {
        UUID id = UUID.randomUUID();
        RegionalResponse response = RegionalResponse.builder()
                .id(id)
                .nom("Ouest")
                .dregionalEnum(DregionalEnum.OUEST)
                .build();

        when(regionalService.getById(id)).thenReturn(response);

        mockMvc.perform(get("/api/v1/regionals/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("Ouest"))
                .andExpect(jsonPath("$.dregionalEnum").value("OUEST"));
    }

    @Test
    void shouldCreateRegional() throws Exception {
        RegionalResponse response = RegionalResponse.builder()
                .id(UUID.randomUUID())
                .nom("Centre")
                .dregionalEnum(DregionalEnum.CENTRE)
                .build();

        when(regionalService.create(any(RegionalCreateRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/regionals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nom": "Centre",
                                  "dregionalEnum": "CENTRE"
                                }"""))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nom").value("Centre"))
                .andExpect(jsonPath("$.dregionalEnum").value("CENTRE"));

        verify(regionalService).create(any(RegionalCreateRequest.class));
    }

    @Test
    void shouldUpdateRegional() throws Exception {
        UUID id = UUID.randomUUID();
        RegionalResponse response = RegionalResponse.builder()
                .id(id)
                .nom("Est")
                .build();

        when(regionalService.update(any(RegionalUpdateRequest.class))).thenReturn(response);

        mockMvc.perform(put("/api/v1/regionals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "id": "%s",
                                  "nom": "Est"
                                }""".formatted(id)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("Est"));

        verify(regionalService).update(any(RegionalUpdateRequest.class));
    }

    @Test
    void shouldDeleteRegional() throws Exception {
        UUID id = UUID.randomUUID();
        doNothing().when(regionalService).delete(id);

        mockMvc.perform(delete("/api/v1/regionals/{id}", id))
                .andExpect(status().isNoContent());

        verify(regionalService).delete(id);
    }

    @Test
    void shouldRejectCreateWhenNomMissing() throws Exception {
        mockMvc.perform(post("/api/v1/regionals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "dregionalEnum": "CENTRE"
                                }"""))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(regionalService);
    }

    @Test
    void shouldRejectUpdateWhenOnlyIdProvided() throws Exception {
        UUID id = UUID.randomUUID();
        mockMvc.perform(put("/api/v1/regionals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "id": "%s"
                                }""".formatted(id)))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(regionalService);
    }
}
