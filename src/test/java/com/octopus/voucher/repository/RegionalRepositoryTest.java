package com.octopus.voucher.repository;

import com.octopus.voucher.entity.Regional;
import com.octopus.voucher.enumeration.DregionalEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RegionalRepositoryTest {
    @Autowired
    private RegionalRepository regionalRepository;

    @Test
    void shouldGetAllRegionals() {
        Regional r1 = Regional.builder()
                .nom("Regional Centre")
                .dregionalEnum(DregionalEnum.CENTRE)
                .createdAt(LocalDateTime.now())
                .build();

        Regional r2 = Regional.builder()
                .nom("Regional Est")
                .dregionalEnum(DregionalEnum.EST)
                .createdAt(LocalDateTime.now())
                .build();

        regionalRepository.saveAll(List.of(r1, r2));

        List<Regional> regionals = regionalRepository.findAll();

        //assert
        assertEquals(2, regionals.size());
        assertTrue(regionals.stream().anyMatch(r -> "Regional Centre".equals(r.getNom())));
    }

    @Test
    void shouldSaveRegional() {
        Regional regional = Regional.builder()
                .nom("Regional Ouest")
                .dregionalEnum(DregionalEnum.OUEST)
                .build();

        Regional saved = regionalRepository.save(regional);

        //assert
        assertNotNull(saved.getId());
        assertEquals("Regional Ouest", saved.getNom());
        assertEquals(DregionalEnum.OUEST, saved.getDregionalEnum());
    }

    @Test
    void shouldDeleteRegional() {
        Regional regional = Regional.builder()
                .nom("Regional Delete")
                .dregionalEnum(DregionalEnum.CENTRE)
                .build();

        Regional saved = regionalRepository.save(regional);
        regionalRepository.delete(saved);

        //assert
        assertFalse(regionalRepository.findById(saved.getId()).isPresent());
    }

    @Test
    void shouldUpdateRegional() {
        Regional regional = Regional.builder()
                .nom("Regional Update")
                .dregionalEnum(DregionalEnum.EST)
                .build();

        Regional saved = regionalRepository.save(regional);
        saved.setNom("Regional Update 2");

        Regional updated = regionalRepository.save(saved);

        //assert
        assertEquals("Regional Update 2", updated.getNom());
    }
}
