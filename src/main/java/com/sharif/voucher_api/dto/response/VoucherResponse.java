package com.sharif.voucher_api.dto.response;

import com.sharif.voucher_api.enumeration.Etat;
import com.sharif.voucher_api.enumeration.Operation;
import com.sharif.voucher_api.enumeration.Plateform;
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
    private Operation operation;
    private Etat etat;
    private Plateform plateform;
    private String numeroTelephone;
    private String transactionId;
    private UUID accountId;
    private LocalDateTime validationDate;
    private LocalDateTime expirationDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
