package com.octopus.voucher.repository;

import com.octopus.voucher.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static com.octopus.voucher.enumeration.RoleEnum.AUDITEUR;
import static com.octopus.voucher.enumeration.StatutEnum.ACTIVE;
import static com.octopus.voucher.enumeration.StatutEnum.DESACTIVE;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void existsByMatricule() {
        assertTrue(userRepository.existsByMatricule("MAT-0001"));
        assertFalse(userRepository.existsByMatricule("MAT-9999"));
    }

    @Test
    void existsByMatriculeAndIdNot() {
        User user = userRepository.findByNom("Doe");
        assertNotNull(user);

        assertTrue(userRepository.existsByMatriculeAndIdNot(user.getMatricule(), UUID.randomUUID()));
        assertFalse(userRepository.existsByMatriculeAndIdNot(user.getMatricule(), user.getId()));
    }

    @Test
    void shouldGetAllUsers() {
        List<User> users = userRepository.findAll();

        //assert
        assertEquals(6, users.size());
        assertEquals("Doe", users.get(0).getNom());
    }

    @Test
    void shouldGetUserByNom() {
        User user = userRepository.findByNom("Doe");

        //assert
        assertNotNull(user);
        assertEquals("John", user.getPrenom());
    }

    @Test
    void shouldSaveUser() {
        User user = User.builder()
                .nom("Smith")
                .prenom("Jane")
                .roleEnum(AUDITEUR)
                .matricule("MAT123")
                .dateNaissance(LocalDate.of(1990, 1, 1))
                .email("jane@gmail.com")
                .numeroTelephone("70000007")
                .statutEnum(ACTIVE)
                .username("Jane.smith")
                .password("password")
                .agence(null)
                .build();

        User savedUser = userRepository.save(user);

        //assert
        assertNotNull(savedUser.getId());
        assertEquals("Smith", savedUser.getNom());
        assertEquals("Jane", savedUser.getPrenom());
        assertEquals(ACTIVE, savedUser.getStatutEnum());
        assertEquals(AUDITEUR, savedUser.getRoleEnum());
        assertEquals("MAT123", savedUser.getMatricule());
    }

    @Test
    void shouldDeleteUser() {
        User user = userRepository.findByNom("Smith");
        userRepository.delete(user);
        User deletedUser = userRepository.findByNom("Smith");

        //assert
        assertNull(deletedUser);

    }

    @Test
    void shouldUpdateUser() {
        User user = userRepository.findByNom("Doe");
        user.setStatutEnum(DESACTIVE);
        User updatedUser = userRepository.save(user);

        //assert
        assertEquals("John", updatedUser.getPrenom());
        assertEquals(DESACTIVE, updatedUser.getStatutEnum());

    }
}
