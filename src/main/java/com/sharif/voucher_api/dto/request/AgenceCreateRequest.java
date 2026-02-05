package com.sharif.voucher_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AgenceCreateRequest {
    @NotBlank
    @Size(min = 2, max = 30)
    private String nom;

    @NotBlank
    @Size(min = 4, max = 50)
    private String code;

    private UUID regionalId;

    @NotEmpty
    private List<UUID> pointVenteIds;
}
