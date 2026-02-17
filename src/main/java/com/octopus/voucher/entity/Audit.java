package com.octopus.voucher.entity;

import com.octopus.voucher.enumeration.EtatEnum;
import com.octopus.voucher.enumeration.OperationEnum;
import com.octopus.voucher.enumeration.PlateformEnum;
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
public class Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String code;

    @Enumerated(EnumType.STRING)
    private OperationEnum operationEnum;

    @Enumerated(EnumType.STRING)
    private EtatEnum etatEnum;

    @Enumerated(EnumType.STRING)
    private PlateformEnum plateformEnum;

    private String numeroTelephone;

    private BigDecimal amount;

    private String transactionId;

    private LocalDateTime eventDate;

    @ManyToOne
    @JoinColumn(name = "voucher_id")
    private Voucher voucher;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
