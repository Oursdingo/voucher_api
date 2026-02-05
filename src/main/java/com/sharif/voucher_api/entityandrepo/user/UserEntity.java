package com.sharif.voucher_api.entityandrepo.user;

import com.sharif.voucher_api.entityandrepo.account.AccountEntity;
import com.sharif.voucher_api.entityandrepo.agence.AgenceEntity;
import com.sharif.voucher_api.entityandrepo.event.EventEntity;
import com.sharif.voucher_api.enumeration.Role;
import com.sharif.voucher_api.enumeration.Statut;
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
@Table(name = "tb_utilisateur")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nom;

    private String prenom;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(unique = true)
    private String matricule;

    private LocalDate dateNaissance;

    private String email;

    private String numeroTelephone;

    @Enumerated(EnumType.STRING)
    private Statut statut;

    private String username;

    private String password;

    @OneToMany(mappedBy = "user")
    private List<AccountEntity> accounts;

    @OneToMany(mappedBy = "user")
    private List<EventEntity> events;

    @ManyToOne
    @JoinColumn(name = "agence_id")
    private AgenceEntity agence;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
