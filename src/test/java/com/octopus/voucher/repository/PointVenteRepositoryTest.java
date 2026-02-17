package com.octopus.voucher.repository;

import com.octopus.voucher.entity.PointVente;
import com.octopus.voucher.enumeration.TypePdvEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PointVenteRepositoryTest {
    @Autowired
    private PointVenteRepository pointVenteRepository;

    @Test
    void existsByName() {
        PointVente pv = PointVente.builder()
                .code("PV-001")
                .name("Point Vente A")
                .salesPointType(TypePdvEnum.ECD)
                .build();

        pointVenteRepository.save(pv);

        assertTrue(pointVenteRepository.existsByName("Point Vente A"));
        assertFalse(pointVenteRepository.existsByName("Point Vente X"));
    }

    @Test
    void existsByCode() {
        PointVente pv = PointVente.builder()
                .code("PV-002")
                .name("Point Vente B")
                .salesPointType(TypePdvEnum.PMU)
                .build();

        pointVenteRepository.save(pv);

        assertTrue(pointVenteRepository.existsByCode("PV-002"));
        assertFalse(pointVenteRepository.existsByCode("PV-999"));
    }

    @Test
    void existsByNameAndIdNot() {
        PointVente pv = PointVente.builder()
                .code("PV-003")
                .name("Point Vente C")
                .salesPointType(TypePdvEnum.LOTO)
                .build();

        PointVente saved = pointVenteRepository.save(pv);

        assertTrue(pointVenteRepository.existsByNameAndIdNot(saved.getName(), UUID.randomUUID()));
        assertFalse(pointVenteRepository.existsByNameAndIdNot(saved.getName(), saved.getId()));
    }

    @Test
    void existsByCodeAndIdNot() {
        PointVente pv = PointVente.builder()
                .code("PV-004")
                .name("Point Vente D")
                .salesPointType(TypePdvEnum.PARI_SPORTIF)
                .build();

        PointVente saved = pointVenteRepository.save(pv);

        assertTrue(pointVenteRepository.existsByCodeAndIdNot(saved.getCode(), UUID.randomUUID()));
        assertFalse(pointVenteRepository.existsByCodeAndIdNot(saved.getCode(), saved.getId()));
    }

    @Test
    void shouldGetAllPointVentes() {
        PointVente pv1 = PointVente.builder()
                .code("PV-005")
                .name("Point Vente E")
                .salesPointType(TypePdvEnum.ECD)
                .build();

        PointVente pv2 = PointVente.builder()
                .code("PV-006")
                .name("Point Vente F")
                .salesPointType(TypePdvEnum.LOTO)
                .build();

        pointVenteRepository.saveAll(List.of(pv1, pv2));

        List<PointVente> points = pointVenteRepository.findAll();

        //assert
        assertEquals(2, points.size());
        assertTrue(points.stream().anyMatch(p -> "PV-005".equals(p.getCode())));
    }

    @Test
    void shouldSavePointVente() {
        PointVente pv = PointVente.builder()
                .code("PV-007")
                .name("Point Vente G")
                .salesPointType(TypePdvEnum.ECD)
                .build();

        PointVente saved = pointVenteRepository.save(pv);

        //assert
        assertNotNull(saved.getId());
        assertEquals("PV-007", saved.getCode());
        assertEquals("Point Vente G", saved.getName());
    }

    @Test
    void shouldDeletePointVente() {
        PointVente pv = PointVente.builder()
                .code("PV-008")
                .name("Point Vente H")
                .salesPointType(TypePdvEnum.PMU)
                .build();

        PointVente saved = pointVenteRepository.save(pv);
        pointVenteRepository.delete(saved);

        //assert
        assertFalse(pointVenteRepository.findById(saved.getId()).isPresent());
    }

    @Test
    void shouldUpdatePointVente() {
        PointVente pv = PointVente.builder()
                .code("PV-009")
                .name("Point Vente I")
                .salesPointType(TypePdvEnum.LOTO)
                .build();

        PointVente saved = pointVenteRepository.save(pv);
        saved.setName("Point Vente I2");

        PointVente updated = pointVenteRepository.save(saved);

        //assert
        assertEquals("Point Vente I2", updated.getName());
    }
}
