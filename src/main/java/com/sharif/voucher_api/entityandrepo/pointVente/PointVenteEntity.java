package com.sharif.voucher_api.entityandrepo.pointVente;

import com.sharif.voucher_api.entityandrepo.agence.AgenceEntity;
import com.sharif.voucher_api.enumeration.TypePdv;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "tb_point_vente")
public class PointVenteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    private String code;

    @Column(unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    private TypePdv salesPointType;

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
