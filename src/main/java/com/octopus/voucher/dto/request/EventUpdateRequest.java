package com.octopus.voucher.dto.request;

import com.octopus.voucher.enumeration.ActionEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
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
public class EventUpdateRequest {
    @NotNull
    private UUID id;

    private ActionEnum actionEnum;

    private String entite;

    private LocalDateTime date;

    private String adresse;

    private UUID userId;

    @AssertTrue
    @JsonIgnore
    public boolean isAtLeastOneFieldPresent() {
        return actionEnum != null
                || entite != null
                || date != null
                || adresse != null
                || userId != null;
    }
}
