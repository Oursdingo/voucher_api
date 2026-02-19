package com.octopus.voucher.controller;

import com.octopus.voucher.dto.request.PointVenteCreateRequest;
import com.octopus.voucher.dto.request.PointVenteUpdateRequest;
import com.octopus.voucher.dto.response.PointVenteResponse;
import com.octopus.voucher.enumeration.TypePdvEnum;
import com.octopus.voucher.service.PointVenteService;
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

@WebMvcTest(PointVenteController.class)
class PointVenteControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PointVenteService pointVenteService;

    @Test
    void shouldReturnAllPointsVente() throws Exception {
        PointVenteResponse response1 = PointVenteResponse.builder().id(UUID.randomUUID()).name("PV A").build();
        PointVenteResponse response2 = PointVenteResponse.builder().id(UUID.randomUUID()).name("PV B").build();

        when(pointVenteService.getAll()).thenReturn(List.of(response1, response2));

        mockMvc.perform(get("/api/v1/points-vente"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value("PV A"))
                .andExpect(jsonPath("$[1].name").value("PV B"));
    }

    @Test
    void shouldReturnPointVenteById() throws Exception {
        UUID id = UUID.randomUUID();
        PointVenteResponse response = PointVenteResponse.builder()
                .id(id)
                .name("PV C")
                .code("PVC-001")
                .salesPointType(TypePdvEnum.ECD)
                .build();

        when(pointVenteService.getById(id)).thenReturn(response);

        mockMvc.perform(get("/api/v1/points-vente/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("PV C"))
                .andExpect(jsonPath("$.code").value("PVC-001"));
    }

    @Test
    void shouldCreatePointVente() throws Exception {
        PointVenteResponse response = PointVenteResponse.builder()
                .id(UUID.randomUUID())
                .name("PV D")
                .code("PVD-001")
                .salesPointType(TypePdvEnum.PMU)
                .build();

        when(pointVenteService.create(any(PointVenteCreateRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/points-vente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "PV D",
                                  "code": "PVD-001",
                                  "salesPointType": "PMU",
                                  "agenceId": "%s"
                                }""".formatted(UUID.randomUUID())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("PV D"))
                .andExpect(jsonPath("$.salesPointType").value("PMU"));

        verify(pointVenteService).create(any(PointVenteCreateRequest.class));
    }

    @Test
    void shouldUpdatePointVente() throws Exception {
        UUID id = UUID.randomUUID();
        PointVenteResponse response = PointVenteResponse.builder()
                .id(id)
                .name("PV E")
                .build();

        when(pointVenteService.update(any(PointVenteUpdateRequest.class))).thenReturn(response);

        mockMvc.perform(put("/api/v1/points-vente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "id": "%s",
                                  "name": "PV E"
                                }""".formatted(id)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("PV E"));

        verify(pointVenteService).update(any(PointVenteUpdateRequest.class));
    }

    @Test
    void shouldDeletePointVente() throws Exception {
        UUID id = UUID.randomUUID();
        doNothing().when(pointVenteService).delete(id);

        mockMvc.perform(delete("/api/v1/points-vente/{id}", id))
                .andExpect(status().isNoContent());

        verify(pointVenteService).delete(id);
    }

    @Test
    void shouldRejectCreateWhenNameMissing() throws Exception {
        mockMvc.perform(post("/api/v1/points-vente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "code": "PVF-001",
                                  "salesPointType": "ECD",
                                  "agenceId": "%s"
                                }""".formatted(UUID.randomUUID())))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(pointVenteService);
    }

    @Test
    void shouldRejectUpdateWhenOnlyIdProvided() throws Exception {
        UUID id = UUID.randomUUID();
        mockMvc.perform(put("/api/v1/points-vente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "id": "%s"
                                }""".formatted(id)))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(pointVenteService);
    }
}
