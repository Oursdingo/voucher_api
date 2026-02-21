package com.octopus.voucher.controller;

import com.octopus.voucher.dto.request.AccountCreateRequest;
import com.octopus.voucher.dto.request.AccountUpdateRequest;
import com.octopus.voucher.dto.response.AccountResponse;
import com.octopus.voucher.enumeration.PlateformEnum;
import com.octopus.voucher.enumeration.StatutEnum;
import com.octopus.voucher.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
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

@WebMvcTest(AccountController.class)
class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;


    @MockitoBean
    private AccountService accountService;

    @Test
    void shouldReturnAllAccounts() throws Exception {
        AccountResponse response1 = AccountResponse.builder()
                .id(UUID.randomUUID())
                .numeroTelephone("70000001")
                .plateformEnum(PlateformEnum.ECD)
                .build();
        AccountResponse response2 = AccountResponse.builder()
                .id(UUID.randomUUID())
                .numeroTelephone("70000002")
                .plateformEnum(PlateformEnum.PMU)
                .build();

        when(accountService.getAll()).thenReturn(List.of(response1, response2));

        mockMvc.perform(get("/api/v1/accounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].numeroTelephone").value("70000001"))
                .andExpect(jsonPath("$[1].numeroTelephone").value("70000002"));
    }

    @Test
    void shouldReturnAccountById() throws Exception {
        UUID id = UUID.randomUUID();
        AccountResponse response = AccountResponse.builder()
                .id(id)
                .numeroTelephone("70000003")
                .plateformEnum(PlateformEnum.LOTO)
                .build();

        when(accountService.getById(id)).thenReturn(response);

        mockMvc.perform(get("/api/v1/accounts/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numeroTelephone").value("70000003"))
                .andExpect(jsonPath("$.plateformEnum").value("LOTO"));
    }

    @Test
    void shouldCreateAccount() throws Exception {
        AccountResponse response = AccountResponse.builder()
                .id(UUID.randomUUID())
                .numeroTelephone("70000004")
                .plateformEnum(PlateformEnum.ECD)
                .statutEnum(StatutEnum.ACTIVE)
                .balance(BigDecimal.valueOf(100))
                .build();

        when(accountService.create(any(AccountCreateRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "numeroTelephone": "70000004",
                                  "plateformEnum": "ECD",
                                  "userId": "%s",
                                  "statutEnum": "ACTIVE",
                                  "balance": 100
                                }""".formatted(UUID.randomUUID())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.numeroTelephone").value("70000004"))
                .andExpect(jsonPath("$.plateformEnum").value("ECD"));

        verify(accountService).create(any(AccountCreateRequest.class));
    }

    @Test
    void shouldUpdateAccount() throws Exception {
        UUID id = UUID.randomUUID();
        AccountResponse response = AccountResponse.builder()
                .id(id)
                .numeroTelephone("70000005")
                .plateformEnum(PlateformEnum.PMU)
                .build();

        when(accountService.update(any(AccountUpdateRequest.class))).thenReturn(response);

        mockMvc.perform(put("/api/v1/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "id": "%s",
                                  "numeroTelephone": "70000005"
                                }""".formatted(id)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numeroTelephone").value("70000005"));

        verify(accountService).update(any(AccountUpdateRequest.class));
    }

    @Test
    void shouldDeleteAccount() throws Exception {
        UUID id = UUID.randomUUID();
        doNothing().when(accountService).delete(id);

        mockMvc.perform(delete("/api/v1/accounts/{id}", id))
                .andExpect(status().isNoContent());

        verify(accountService).delete(id);
    }

    @Test
    void shouldRejectCreateWhenInvalidPhone() throws Exception {
        mockMvc.perform(post("/api/v1/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "numeroTelephone": "123",
                                  "plateformEnum": "ECD",
                                  "userId": "%s"
                                }""".formatted(UUID.randomUUID())))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(accountService);
    }

    @Test
    void shouldRejectUpdateWhenOnlyIdProvided() throws Exception {
        UUID id = UUID.randomUUID();
        mockMvc.perform(put("/api/v1/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "id": "%s"
                                }""".formatted(id)))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(accountService);
    }
}
