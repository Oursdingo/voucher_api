package com.sharif.voucher_api.entityandrepo.account;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, UUID> {
    Optional<AccountEntity> findByNumeroTelephoneAndPlateform(String numeroTelephone, com.sharif.voucher_api.enumeration.Plateform plateform);
}
