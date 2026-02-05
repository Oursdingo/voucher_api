package com.sharif.voucher_api.entityandrepo.agence;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface AgenceRepository extends JpaRepository<AgenceEntity, UUID> {
    boolean existsByNom(String nom);
    boolean existsByCode(String code);
    boolean existsByNomAndIdNot(String nom, UUID id);
    boolean existsByCodeAndIdNot(String code, UUID id);
}
