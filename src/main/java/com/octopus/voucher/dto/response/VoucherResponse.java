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
public class VoucherResponse {
    private UUID id;
    private String code;
    private BigDecimal amount;
    private OperationEnum operationEnum;
    private EtatEnum etatEnum;
    private PlateformEnum plateformEnum;
    private String numeroTelephone;
    private String transactionId;
    private UUID accountId;
    private LocalDateTime validationDate;
    private LocalDateTime expirationDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
