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
public class AuditResponse {
    private UUID id;
    private String code;
    private Operation operation;
    private Etat etat;
    private Plateform plateform;
    private String numeroTelephone;
    private BigDecimal amount;
    private String transactionId;
    private LocalDateTime eventDate;
    private UUID voucherId;
    private UUID userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
