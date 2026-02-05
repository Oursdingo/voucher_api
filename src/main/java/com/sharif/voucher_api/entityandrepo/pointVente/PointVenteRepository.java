package com.sharif.voucher_api.entityandrepo.pointVente;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface PointVenteRepository extends JpaRepository<PointVenteEntity, UUID> {
    boolean existsByName(String name);
    boolean existsByCode(String code);
    boolean existsByNameAndIdNot(String name, UUID id);
    boolean existsByCodeAndIdNot(String code, UUID id);
}
