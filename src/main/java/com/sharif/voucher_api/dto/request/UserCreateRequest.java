package com.sharif.voucher_api.dto.request;

import com.sharif.voucher_api.enumeration.Role;
import com.sharif.voucher_api.enumeration.Statut;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.AssertTrue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCreateRequest {
    @NotBlank
    @Size(min = 2, max = 30)
    private String nom;

    @NotBlank
    @Size(min = 4, max = 50)
    private String prenom;

    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9]{8,10}$")
    private String matricule;

    @NotNull
    @Past
    private LocalDate dateNaissance;

    @NotNull
    private Role role;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "^[0-9]{8}$")
    private String numeroTelephone;

    private Statut statut;

    private String username;

    private UUID agenceId;

    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{10,}$")
    private String password;

    @AssertTrue
    @JsonIgnore
    public boolean isAgeValid() {
        if (dateNaissance == null) {
            return false;
        }
        return !dateNaissance.plusYears(21).isAfter(LocalDate.now());
    }
}
