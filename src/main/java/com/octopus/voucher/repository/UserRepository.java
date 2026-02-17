package com.octopus.voucher.repository;


import com.octopus.voucher.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByMatricule(String matricule);
    boolean existsByMatriculeAndIdNot(String matricule, UUID id);

    User findByNom(String nom);
}
