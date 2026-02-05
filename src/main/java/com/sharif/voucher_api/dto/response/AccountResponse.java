package com.sharif.voucher_api.dto.response;

import com.sharif.voucher_api.enumeration.Plateform;
import com.sharif.voucher_api.enumeration.Statut;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountResponse {
    private UUID id;
    private String numeroTelephone;
    private Statut statut;
    private String observation;
    private BigDecimal balance;
    private Plateform plateform;
    private UUID userId;
    private List<UUID> voucherIds;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
