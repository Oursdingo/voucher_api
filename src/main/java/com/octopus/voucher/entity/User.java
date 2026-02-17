package com.octopus.voucher.entity;

import com.octopus.voucher.enumeration.RoleEnum;
import com.octopus.voucher.enumeration.StatutEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "tb_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nom;

    private String prenom;

    @Enumerated(EnumType.STRING)
    private RoleEnum roleEnum;

    @Column(unique = true)
    private String matricule;

    private LocalDate dateNaissance;

    private String email;

    private String numeroTelephone;

    @Enumerated(EnumType.STRING)
    private StatutEnum statutEnum;

    private String username;

    private String password;

    @OneToMany(mappedBy = "user")
    private List<Account> accounts;

    @OneToMany(mappedBy = "user")
    private List<Event> events;

    @ManyToOne
    @JoinColumn(name = "agence_id")
    private Agence agence;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
