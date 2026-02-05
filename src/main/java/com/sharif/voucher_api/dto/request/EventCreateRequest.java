package com.sharif.voucher_api.dto.request;

import com.sharif.voucher_api.enumeration.Action;
import jakarta.validation.constraints.NotBlank;
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
public class EventCreateRequest {
    @NotNull
    private Action action;

    @NotBlank
    private String entite;

    @NotNull
    private LocalDateTime date;

    @NotBlank
    private String adresse;

    @NotNull
    private UUID userId;
}
