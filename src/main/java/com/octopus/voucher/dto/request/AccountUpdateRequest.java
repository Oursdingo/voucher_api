package com.octopus.voucher.dto.request;

import com.octopus.voucher.enumeration.PlateformEnum;
import com.octopus.voucher.enumeration.StatutEnum;
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

    private StatutEnum statutEnum;

    private String observation;

    private BigDecimal balance;

    private PlateformEnum plateformEnum;

    private UUID userId;

    @AssertTrue
    @JsonIgnore
    public boolean isAtLeastOneFieldPresent() {
        return numeroTelephone != null
                || statutEnum != null
                || observation != null
                || balance != null
                || plateformEnum != null
                || userId != null;
    }
}
