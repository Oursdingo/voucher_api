package com.sharif.voucher_api.dto.request;

import com.sharif.voucher_api.enumeration.Etat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
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
public class AuditUpdateRequest {
    @NotNull
    private UUID id;

    private Etat etat;

    private String transactionId;

    private LocalDateTime eventDate;

    @AssertTrue
    @JsonIgnore
    public boolean isAtLeastOneFieldPresent() {
        return etat != null || transactionId != null || eventDate != null;
    }
}
