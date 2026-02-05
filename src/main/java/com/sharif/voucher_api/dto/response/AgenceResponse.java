package com.sharif.voucher_api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AgenceResponse {
    private UUID id;
    private String nom;
    private String code;
    private UUID regionalId;
    private List<UUID> pointVenteIds;
    private List<UUID> userIds;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
