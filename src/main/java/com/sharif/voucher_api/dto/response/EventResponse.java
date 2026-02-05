package com.sharif.voucher_api.dto.response;

import com.sharif.voucher_api.enumeration.Action;
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
    private Action action;
    private String entite;
    private LocalDateTime date;
    private String adresse;
    private UUID userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
