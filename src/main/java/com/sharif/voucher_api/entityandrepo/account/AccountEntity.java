package com.sharif.voucher_api.entityandrepo.account;

import com.sharif.voucher_api.entityandrepo.user.UserEntity;
import com.sharif.voucher_api.entityandrepo.voucher.VoucherEntity;
import com.sharif.voucher_api.enumeration.Plateform;
import com.sharif.voucher_api.enumeration.Statut;
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
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "tb_account")
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String numeroTelephone;

    @Enumerated(EnumType.STRING)
    private Statut statut;

    private String observation;

    private BigDecimal balance;


    @OneToMany(mappedBy = "account")
    private List<VoucherEntity> vouchers;

    @Enumerated(EnumType.STRING)
    private Plateform plateform;

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
