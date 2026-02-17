package com.octopus.voucher.dto.response;

import com.octopus.voucher.enumeration.EtatEnum;
import com.octopus.voucher.enumeration.OperationEnum;
import com.octopus.voucher.enumeration.PlateformEnum;
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
public class AuditResponse {
    private UUID id;
    private String code;
    private OperationEnum operationEnum;
    private EtatEnum etatEnum;
    private PlateformEnum plateformEnum;
    private String numeroTelephone;
    private BigDecimal amount;
    private String transactionId;
    private LocalDateTime eventDate;
    private UUID voucherId;
    private UUID userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
