package com.octopus.voucher.dto.response;

import com.octopus.voucher.enumeration.TypePdvEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PointVenteResponse {
    private UUID id;
    private String name;
    private String code;
    private TypePdvEnum salesPointType;
    private UUID agenceId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
