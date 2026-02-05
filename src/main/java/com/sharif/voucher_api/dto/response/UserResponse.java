package com.sharif.voucher_api.dto.response;

import com.sharif.voucher_api.enumeration.Role;
import com.sharif.voucher_api.enumeration.Statut;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private UUID id;
    private String nom;
    private String prenom;
    private String matricule;
    private LocalDate dateNaissance;
    private Role role;
    private String email;
    private String numeroTelephone;
    private Statut statut;
    private String username;
    private UUID agenceId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
