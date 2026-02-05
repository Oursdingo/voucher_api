package com.sharif.voucher_api.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
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
public class AgenceUpdateRequest {
    @NotNull
    private UUID id;

    @Size(min = 2, max = 30)
    private String nom;

    @Size(min = 4, max = 50)
    private String code;

    private UUID regionalId;

    private List<UUID> pointVenteIds;

    @AssertTrue
    @JsonIgnore
    public boolean isAtLeastOneFieldPresent() {
        return nom != null
                || code != null
                || regionalId != null
                || pointVenteIds != null;
    }
}
