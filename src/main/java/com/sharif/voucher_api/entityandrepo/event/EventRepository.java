package com.sharif.voucher_api.entityandrepo.event;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface EventRepository extends JpaRepository<EventEntity, UUID> {
}
