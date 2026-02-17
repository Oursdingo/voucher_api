package com.octopus.voucher.repository;

import com.octopus.voucher.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface VoucherRepository extends JpaRepository<Voucher, UUID> {
}
