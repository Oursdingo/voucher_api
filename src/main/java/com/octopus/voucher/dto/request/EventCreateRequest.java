package com.octopus.voucher.dto.request;

import com.octopus.voucher.enumeration.ActionEnum;
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
    private ActionEnum actionEnum;

    @NotBlank
    private String entite;

    @NotNull
    private LocalDateTime date;

    @NotBlank
    private String adresse;

    @NotNull
    private UUID userId;
}
