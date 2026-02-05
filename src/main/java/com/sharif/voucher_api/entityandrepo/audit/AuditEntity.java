package com.sharif.voucher_api.entityandrepo.audit;

import com.sharif.voucher_api.entityandrepo.user.UserEntity;
import com.sharif.voucher_api.entityandrepo.voucher.VoucherEntity;
import com.sharif.voucher_api.enumeration.Etat;
import com.sharif.voucher_api.enumeration.Operation;
import com.sharif.voucher_api.enumeration.Plateform;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "tb_audit")
public class AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String code;

    @Enumerated(EnumType.STRING)
    private Operation operation;

    @Enumerated(EnumType.STRING)
    private Etat etat;

    @Enumerated(EnumType.STRING)
    private Plateform plateform;

    private String numeroTelephone;

    private BigDecimal amount;

    private String transactionId;

    private LocalDateTime eventDate;

    @ManyToOne
    @JoinColumn(name = "voucher_id")
    private VoucherEntity voucher;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
