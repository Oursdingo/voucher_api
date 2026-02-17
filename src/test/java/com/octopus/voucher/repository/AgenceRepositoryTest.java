package com.octopus.voucher.repository;

import com.octopus.voucher.entity.Agence;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AgenceRepositoryTest {
    @Autowired
    private AgenceRepository agenceRepository;

    @Test
    void existsByNom() {
        Agence agence = Agence.builder()
                .code("AG-001")
                .nom("Agence Centre")
                .build();

        agenceRepository.save(agence);

        assertTrue(agenceRepository.existsByNom("Agence Centre"));
        assertFalse(agenceRepository.existsByNom("Agence Inconnue"));
    }

    @Test
    void existsByCode() {
        Agence agence = Agence.builder()
                .code("AG-002")
                .nom("Agence Est")
                .build();

        agenceRepository.save(agence);

        assertTrue(agenceRepository.existsByCode("AG-002"));
        assertFalse(agenceRepository.existsByCode("AG-999"));
    }

    @Test
    void existsByNomAndIdNot() {
        Agence agence = Agence.builder()
                .code("AG-003")
                .nom("Agence Ouest")
                .build();

        Agence saved = agenceRepository.save(agence);

        assertTrue(agenceRepository.existsByNomAndIdNot(saved.getNom(), UUID.randomUUID()));
        assertFalse(agenceRepository.existsByNomAndIdNot(saved.getNom(), saved.getId()));
    }

    @Test
    void existsByCodeAndIdNot() {
        Agence agence = Agence.builder()
                .code("AG-004")
                .nom("Agence Sud")
                .build();

        Agence saved = agenceRepository.save(agence);

        assertTrue(agenceRepository.existsByCodeAndIdNot(saved.getCode(), UUID.randomUUID()));
        assertFalse(agenceRepository.existsByCodeAndIdNot(saved.getCode(), saved.getId()));
    }

    @Test
    void shouldGetAllAgences() {
        Agence agence1 = Agence.builder()
                .code("AG-005")
                .nom("Agence Nord")
                .build();

        Agence agence2 = Agence.builder()
                .code("AG-006")
                .nom("Agence Centre 2")
                .build();

        agenceRepository.saveAll(List.of(agence1, agence2));

        List<Agence> agences = agenceRepository.findAll();

        //assert
        assertEquals(2, agences.size());
        assertTrue(agences.stream().anyMatch(a -> "AG-005".equals(a.getCode())));
    }

    @Test
    void shouldSaveAgence() {
        Agence agence = Agence.builder()
                .code("AG-007")
                .nom("Agence Test")
                .build();

        Agence saved = agenceRepository.save(agence);

        //assert
        assertNotNull(saved.getId());
        assertEquals("AG-007", saved.getCode());
        assertEquals("Agence Test", saved.getNom());
    }

    @Test
    void shouldDeleteAgence() {
        Agence agence = Agence.builder()
                .code("AG-008")
                .nom("Agence Delete")
                .build();

        Agence saved = agenceRepository.save(agence);
        agenceRepository.delete(saved);

        //assert
        assertFalse(agenceRepository.findById(saved.getId()).isPresent());
    }

    @Test
    void shouldUpdateAgence() {
        Agence agence = Agence.builder()
                .code("AG-009")
                .nom("Agence Update")
                .build();

        Agence saved = agenceRepository.save(agence);
        saved.setNom("Agence Update 2");

        Agence updated = agenceRepository.save(saved);

        //assert
        assertEquals("Agence Update 2", updated.getNom());
    }
}
