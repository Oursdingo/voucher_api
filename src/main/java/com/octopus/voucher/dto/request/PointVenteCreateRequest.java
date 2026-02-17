package com.octopus.voucher.dto.request;

import com.octopus.voucher.enumeration.TypePdvEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PointVenteCreateRequest {
    @NotBlank
    @Size(min = 2, max = 30)
    private String name;

    @NotBlank
    @Size(min = 4, max = 50)
    private String code;

    @NotNull
    private TypePdvEnum salesPointType;

    @NotNull
    private UUID agenceId;
}
