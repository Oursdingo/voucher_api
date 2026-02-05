package com.sharif.voucher_api.dto.response;

import com.sharif.voucher_api.enumeration.Dregional;
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
public class RegionalResponse {
    private UUID id;
    private String nom;
    private Dregional dregional;
    private List<UUID> agenceIds;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
