package com.sharif.voucher_api.entityandrepo.regionalManagement;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface RegionalRepository extends JpaRepository<RegionalEntity, UUID> {
}
