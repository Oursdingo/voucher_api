package com.octopus.voucher.entity;

import com.octopus.voucher.enumeration.OperationEnum;
import com.octopus.voucher.enumeration.EtatEnum;
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
@Table(name = "tb_voucher")
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    private  String code;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private OperationEnum operationEnum;

    @Enumerated(EnumType.STRING)
    private EtatEnum etatEnum;

    @Enumerated(EnumType.STRING)
    private PlateformEnum plateformEnum;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    private String numeroTelephone;

    private String transactionId;

    private LocalDateTime validationDate;

    private LocalDateTime expirationDate;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
