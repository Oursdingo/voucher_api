package com.octopus.voucher.dto.request;

import com.octopus.voucher.enumeration.EtatEnum;
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

    private EtatEnum etatEnum;

    private String transactionId;

    private LocalDateTime eventDate;

    @AssertTrue
    @JsonIgnore
    public boolean isAtLeastOneFieldPresent() {
        return etatEnum != null || transactionId != null || eventDate != null;
    }
}
