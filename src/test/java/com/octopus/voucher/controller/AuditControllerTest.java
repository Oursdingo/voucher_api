package com.octopus.voucher.controller;

import com.octopus.voucher.dto.request.AuditCreateRequest;
import com.octopus.voucher.dto.request.AuditUpdateRequest;
import com.octopus.voucher.dto.response.AuditResponse;
import com.octopus.voucher.enumeration.EtatEnum;
import com.octopus.voucher.enumeration.OperationEnum;
import com.octopus.voucher.enumeration.PlateformEnum;
import com.octopus.voucher.service.AuditService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
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

@WebMvcTest(AuditController.class)
class AuditControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuditService auditService;

    @Test
    void shouldReturnAllAudits() throws Exception {
        AuditResponse response1 = AuditResponse.builder().id(UUID.randomUUID()).code("AUD-001").build();
        AuditResponse response2 = AuditResponse.builder().id(UUID.randomUUID()).code("AUD-002").build();

        when(auditService.getAll()).thenReturn(List.of(response1, response2));

        mockMvc.perform(get("/api/v1/audits"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].code").value("AUD-001"))
                .andExpect(jsonPath("$[1].code").value("AUD-002"));
    }

    @Test
    void shouldReturnAuditById() throws Exception {
        UUID id = UUID.randomUUID();
        AuditResponse response = AuditResponse.builder()
                .id(id)
                .code("AUD-003")
                .operationEnum(OperationEnum.RECHARGE)
                .etatEnum(EtatEnum.VALID)
                .plateformEnum(PlateformEnum.ECD)
                .build();

        when(auditService.getById(id)).thenReturn(response);

        mockMvc.perform(get("/api/v1/audits/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("AUD-003"))
                .andExpect(jsonPath("$.operationEnum").value("RECHARGE"));
    }

    @Test
    void shouldCreateAudit() throws Exception {
        AuditResponse response = AuditResponse.builder()
                .id(UUID.randomUUID())
                .code("AUD-004")
                .build();

        when(auditService.create(any(AuditCreateRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/audits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "code": "AUD-004",
                                  "operationEnum": "RECHARGE",
                                  "etatEnum": "VALID",
                                  "plateformEnum": "ECD",
                                  "numeroTelephone": "70000006",
                                  "amount": 500,
                                  "eventDate": "%s"
                                }""".formatted(LocalDateTime.now().minusDays(1))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value("AUD-004"));

        verify(auditService).create(any(AuditCreateRequest.class));
    }

    @Test
    void shouldUpdateAudit() throws Exception {
        UUID id = UUID.randomUUID();
        AuditResponse response = AuditResponse.builder()
                .id(id)
                .etatEnum(EtatEnum.NON_VALIDE)
                .build();

        when(auditService.update(any(AuditUpdateRequest.class))).thenReturn(response);

        mockMvc.perform(put("/api/v1/audits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "id": "%s",
                                  "etatEnum": "NON_VALIDE"
                                }""".formatted(id)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.etatEnum").value("NON_VALIDE"));

        verify(auditService).update(any(AuditUpdateRequest.class));
    }

    @Test
    void shouldDeleteAudit() throws Exception {
        UUID id = UUID.randomUUID();
        doNothing().when(auditService).delete(id);

        mockMvc.perform(delete("/api/v1/audits/{id}", id))
                .andExpect(status().isNoContent());

        verify(auditService).delete(id);
    }

    @Test
    void shouldRejectCreateWhenInvalidPhone() throws Exception {
        mockMvc.perform(post("/api/v1/audits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "code": "AUD-005",
                                  "operationEnum": "RECHARGE",
                                  "etatEnum": "VALID",
                                  "plateformEnum": "ECD",
                                  "numeroTelephone": "123",
                                  "amount": 100,
                                  "eventDate": "%s"
                                }""".formatted(LocalDateTime.now().minusDays(1))))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(auditService);
    }

    @Test
    void shouldRejectUpdateWhenOnlyIdProvided() throws Exception {
        UUID id = UUID.randomUUID();
        mockMvc.perform(put("/api/v1/audits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "id": "%s"
                                }""".formatted(id)))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(auditService);
    }
}
