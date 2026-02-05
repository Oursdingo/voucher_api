package com.sharif.voucher_api.entityandrepo.agence;

import com.sharif.voucher_api.entityandrepo.event.EventEntity;
import com.sharif.voucher_api.entityandrepo.pointVente.PointVenteEntity;
import com.sharif.voucher_api.entityandrepo.regionalManagement.RegionalEntity;
import com.sharif.voucher_api.entityandrepo.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tb_agence")
public class AgenceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    private String code;

    @Column(unique = true)
    private String nom;

    @OneToMany(mappedBy = "agence")
    private List<UserEntity> users;

    @OneToMany(mappedBy = "agence")
    private List<PointVenteEntity> pointVentes;

    @ManyToOne
    @JoinColumn(name = "regional_id")
    private RegionalEntity regional;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
