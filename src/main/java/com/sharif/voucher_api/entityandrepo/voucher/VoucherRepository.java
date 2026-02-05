package com.sharif.voucher_api.entityandrepo.voucher;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface VoucherRepository extends JpaRepository<VoucherEntity, UUID> {
}
