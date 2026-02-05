package com.sharif.voucher_api.dto.request;

import com.sharif.voucher_api.enumeration.Dregional;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegionalCreateRequest {
    @NotBlank
    @Size(min = 2, max = 50)
    private String nom;

    @NotNull
    private Dregional dregional;
}
