package com.sharif.voucher_api.dto.request;

import com.sharif.voucher_api.enumeration.Plateform;
import com.sharif.voucher_api.enumeration.Statut;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountCreateRequest {
    @NotBlank
    @Pattern(regexp = "^[0-9]{8}$")
    private String numeroTelephone;

    private Statut statut;

    private String observation;

    private BigDecimal balance;

    @NotNull
    private Plateform plateform;

    @NotNull
    private UUID userId;
}
