package com.octopus.voucher.controller;

import com.octopus.voucher.dto.request.VoucherCreateRequest;
import com.octopus.voucher.dto.request.VoucherUpdateRequest;
import com.octopus.voucher.dto.response.VoucherResponse;
import com.octopus.voucher.enumeration.EtatEnum;
import com.octopus.voucher.enumeration.OperationEnum;
import com.octopus.voucher.enumeration.PlateformEnum;
import com.octopus.voucher.service.VoucherService;
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

@WebMvcTest(VoucherController.class)
class VoucherControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private VoucherService voucherService;

    @Test
    void shouldReturnAllVouchers() throws Exception {
        VoucherResponse response1 = VoucherResponse.builder().id(UUID.randomUUID()).code("VCH-001").build();
        VoucherResponse response2 = VoucherResponse.builder().id(UUID.randomUUID()).code("VCH-002").build();

        when(voucherService.getAll()).thenReturn(List.of(response1, response2));

        mockMvc.perform(get("/api/v1/vouchers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].code").value("VCH-001"))
                .andExpect(jsonPath("$[1].code").value("VCH-002"));
    }

    @Test
    void shouldReturnVoucherById() throws Exception {
        UUID id = UUID.randomUUID();
        VoucherResponse response = VoucherResponse.builder()
                .id(id)
                .code("VCH-003")
                .etatEnum(EtatEnum.VALID)
                .build();

        when(voucherService.getById(id)).thenReturn(response);

        mockMvc.perform(get("/api/v1/vouchers/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("VCH-003"))
                .andExpect(jsonPath("$.etatEnum").value("VALID"));
    }

    @Test
    void shouldCreateVoucher() throws Exception {
        VoucherResponse response = VoucherResponse.builder()
                .id(UUID.randomUUID())
                .code("VCH-004")
                .operationEnum(OperationEnum.RECHARGE)
                .amount(BigDecimal.valueOf(150))
                .build();

        when(voucherService.create(any(VoucherCreateRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/vouchers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "numeroTelephone": "70000007",
                                  "amount": 150,
                                  "plateformEnum": "ECD",
                                  "operationEnum": "RECHARGE",
                                  "expirationDate": "%s"
                                }""".formatted(LocalDateTime.now().plusDays(2))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value("VCH-004"));

        verify(voucherService).create(any(VoucherCreateRequest.class));
    }

    @Test
    void shouldUpdateVoucher() throws Exception {
        UUID id = UUID.randomUUID();
        VoucherResponse response = VoucherResponse.builder()
                .id(id)
                .etatEnum(EtatEnum.NON_VALIDE)
                .build();

        when(voucherService.update(any(VoucherUpdateRequest.class))).thenReturn(response);

        mockMvc.perform(put("/api/v1/vouchers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "id": "%s",
                                  "etatEnum": "NON_VALIDE"
                                }""".formatted(id)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.etatEnum").value("NON_VALIDE"));

        verify(voucherService).update(any(VoucherUpdateRequest.class));
    }

    @Test
    void shouldDeleteVoucher() throws Exception {
        UUID id = UUID.randomUUID();
        doNothing().when(voucherService).delete(id);

        mockMvc.perform(delete("/api/v1/vouchers/{id}", id))
                .andExpect(status().isNoContent());

        verify(voucherService).delete(id);
    }

    @Test
    void shouldRejectCreateWhenAmountNotMultipleOfFifty() throws Exception {
        mockMvc.perform(post("/api/v1/vouchers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "numeroTelephone": "70000007",
                                  "amount": 125,
                                  "plateformEnum": "ECD",
                                  "operationEnum": "RECHARGE",
                                  "expirationDate": "%s"
                                }""".formatted(LocalDateTime.now().plusDays(2))))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(voucherService);
    }

    @Test
    void shouldRejectUpdateWhenOnlyIdProvided() throws Exception {
        UUID id = UUID.randomUUID();
        mockMvc.perform(put("/api/v1/vouchers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "id": "%s"
                                }""".formatted(id)))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(voucherService);
    }
}
