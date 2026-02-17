package com.octopus.voucher.repository;


import com.octopus.voucher.entity.Account;
import com.octopus.voucher.enumeration.PlateformEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
    Optional<Account> findByNumeroTelephoneAndPlateform(String numeroTelephone, PlateformEnum plateformEnum);
}
