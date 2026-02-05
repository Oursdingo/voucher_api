package com.sharif.voucher_api.dto.request;

import com.sharif.voucher_api.enumeration.Etat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VoucherUpdateRequest {
    @NotNull
    private UUID id;

    @Pattern(regexp = "^[A-Za-z0-9]{8,10}$")
    private String code;

    private Etat etat;

    private String transactionId;

    private LocalDateTime validationDate;

    private LocalDateTime expirationDate;

    @AssertTrue
    @JsonIgnore
    public boolean isAtLeastOneFieldPresent() {
        return code != null
                || etat != null
                || transactionId != null
                || validationDate != null
                || expirationDate != null;
    }
}
