package com.octopus.voucher.dto.response;

import com.octopus.voucher.enumeration.ActionEnum;
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
public class EventResponse {
    private UUID id;
    private ActionEnum actionEnum;
    private String entite;
    private LocalDateTime date;
    private String adresse;
    private UUID userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
