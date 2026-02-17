package com.octopus.voucher.dto.response;

import com.octopus.voucher.enumeration.PlateformEnum;
import com.octopus.voucher.enumeration.StatutEnum;
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
    private StatutEnum statutEnum;
    private String observation;
    private BigDecimal balance;
    private PlateformEnum plateformEnum;
    private UUID userId;
    private List<UUID> voucherIds;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
