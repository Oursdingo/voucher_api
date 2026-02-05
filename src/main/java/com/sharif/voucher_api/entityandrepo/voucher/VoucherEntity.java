package com.sharif.voucher_api.entityandrepo.voucher;

import com.sharif.voucher_api.entityandrepo.account.AccountEntity;
import com.sharif.voucher_api.enumeration.Operation;
import com.sharif.voucher_api.enumeration.Etat;
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
@Table(name = "tb_voucher")
public class VoucherEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    private  String code;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private Operation operation;

    @Enumerated(EnumType.STRING)
    private Etat etat;

    @Enumerated(EnumType.STRING)
    private Plateform plateform;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private AccountEntity account;

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
