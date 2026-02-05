package com.sharif.voucher_api.dto.request;

import com.sharif.voucher_api.enumeration.Dregional;
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
public class RegionalUpdateRequest {
    @NotNull
    private UUID id;

    @Size(min = 2, max = 50)
    private String nom;

    private Dregional dregional;

    @AssertTrue
    @JsonIgnore
    public boolean isAtLeastOneFieldPresent() {
        return nom != null || dregional != null;
    }
}
