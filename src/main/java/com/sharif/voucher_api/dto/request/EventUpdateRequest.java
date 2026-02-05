package com.sharif.voucher_api.dto.request;

import com.sharif.voucher_api.enumeration.Action;
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

    private Action action;

    private String entite;

    private LocalDateTime date;

    private String adresse;

    private UUID userId;

    @AssertTrue
    @JsonIgnore
    public boolean isAtLeastOneFieldPresent() {
        return action != null
                || entite != null
                || date != null
                || adresse != null
                || userId != null;
    }
}
