package com.sharif.voucher_api.dto.request;

import com.sharif.voucher_api.enumeration.Plateform;
import com.sharif.voucher_api.enumeration.Statut;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.AssertTrue;
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
public class AccountUpdateRequest {
    @NotNull
    private UUID id;

    @Pattern(regexp = "^[0-9]{8}$")
    private String numeroTelephone;

    private Statut statut;

    private String observation;

    private BigDecimal balance;

    private Plateform plateform;

    private UUID userId;

    @AssertTrue
    @JsonIgnore
    public boolean isAtLeastOneFieldPresent() {
        return numeroTelephone != null
                || statut != null
                || observation != null
                || balance != null
                || plateform != null
                || userId != null;
    }
}
