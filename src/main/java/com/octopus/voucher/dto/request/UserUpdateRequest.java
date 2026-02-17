package com.octopus.voucher.dto.request;

import com.octopus.voucher.enumeration.RoleEnum;
import com.octopus.voucher.enumeration.StatutEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Past;
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
public class UserUpdateRequest {
    @NotNull
    private UUID id;

    @Size(min = 2, max = 30)
    private String nom;

    @Size(min = 4, max = 50)
    private String prenom;

    @Pattern(regexp = "^[A-Za-z0-9]{8,10}$")
    private String matricule;

    @Past
    private LocalDate dateNaissance;

    private RoleEnum roleEnum;

    @Email
    private String email;

    @Pattern(regexp = "^[0-9]{8}$")
    private String numeroTelephone;

    private StatutEnum statutEnum;

    private String username;

    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{10,}$")
    private String password;

    private UUID agenceId;

    @AssertTrue
    @JsonIgnore
    public boolean isAtLeastOneFieldPresent() {
        return nom != null
                || prenom != null
                || matricule != null
                || dateNaissance != null
                || roleEnum != null
                || email != null
                || numeroTelephone != null
                || statutEnum != null
                || username != null
                || password != null
                || agenceId != null;
    }

    @AssertTrue
    @JsonIgnore
    public boolean isAgeValidIfProvided() {
        if (dateNaissance == null) {
            return true;
        }
        return !dateNaissance.plusYears(21).isAfter(LocalDate.now());
    }
}
