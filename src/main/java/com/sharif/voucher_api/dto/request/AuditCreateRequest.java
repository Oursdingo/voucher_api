package com.sharif.voucher_api.dto.request;

import com.sharif.voucher_api.enumeration.Etat;
import com.sharif.voucher_api.enumeration.Operation;
import com.sharif.voucher_api.enumeration.Plateform;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuditCreateRequest {
    @NotBlank
    private String code;

    @NotNull
    private Operation operation;

    @NotNull
    private Etat etat;

    @NotNull
    private Plateform plateform;

    @NotBlank
    @Pattern(regexp = "^[0-9]{8}$")
    private String numeroTelephone;

    @NotNull
    private BigDecimal amount;

    private String transactionId;

    @NotNull
    private LocalDateTime eventDate;

    private UUID voucherId;

    private UUID userId;
}
