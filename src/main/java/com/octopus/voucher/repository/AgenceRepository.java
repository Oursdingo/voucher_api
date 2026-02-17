package com.octopus.voucher.repository;


import com.octopus.voucher.entity.Agence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface AgenceRepository extends JpaRepository<Agence, UUID> {
    boolean existsByNom(String nom);
    boolean existsByCode(String code);
    boolean existsByNomAndIdNot(String nom, UUID id);
    boolean existsByCodeAndIdNot(String code, UUID id);
}
