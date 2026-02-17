package com.octopus.voucher.dto.request;

import com.octopus.voucher.enumeration.TypePdvEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.AssertTrue;
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
public class PointVenteUpdateRequest {
    @NotNull
    private UUID id;

    @Size(min = 2, max = 30)
    private String name;

    @Size(min = 4, max = 50)
    private String code;

    private TypePdvEnum salesPointType;

    private UUID agenceId;

    @AssertTrue
    @JsonIgnore
    public boolean isAtLeastOneFieldPresent() {
        return name != null
                || code != null
                || salesPointType != null
                || agenceId != null;
    }
}
