package com.octopus.voucher.dto.request;

import com.octopus.voucher.enumeration.EtatEnum;
import com.octopus.voucher.enumeration.OperationEnum;
import com.octopus.voucher.enumeration.PlateformEnum;
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
    private OperationEnum operationEnum;

    @NotNull
    private EtatEnum etatEnum;

    @NotNull
    private PlateformEnum plateformEnum;

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
