package com.octopus.voucher.repository;

import com.octopus.voucher.entity.Audit;
import com.octopus.voucher.enumeration.EtatEnum;
import com.octopus.voucher.enumeration.OperationEnum;
import com.octopus.voucher.enumeration.PlateformEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AuditRepositoryTest {
    @Autowired
    private AuditRepository auditRepository;

    @Test
    void shouldGetAllAudits() {
        Audit audit1 = Audit.builder()
                .code("AUD-001")
                .operationEnum(OperationEnum.RECHARGE)
                .etatEnum(EtatEnum.VALID)
                .plateformEnum(PlateformEnum.ECD)
                .numeroTelephone("70000020")
                .amount(new BigDecimal("500.00"))
                .transactionId("TX-001")
                .eventDate(LocalDateTime.of(2026, 1, 1, 10, 0))
                .build();

        Audit audit2 = Audit.builder()
                .code("AUD-002")
                .operationEnum(OperationEnum.RETRAIT)
                .etatEnum(EtatEnum.NON_VALIDE)
                .plateformEnum(PlateformEnum.PMU)
                .numeroTelephone("70000021")
                .amount(new BigDecimal("250.00"))
                .transactionId("TX-002")
                .eventDate(LocalDateTime.of(2026, 1, 2, 11, 0))
                .build();

        auditRepository.saveAll(List.of(audit1, audit2));

        List<Audit> audits = auditRepository.findAll();

        //assert
        assertEquals(2, audits.size());
        assertTrue(audits.stream().anyMatch(a -> "AUD-001".equals(a.getCode())));
    }

    @Test
    void shouldSaveAudit() {
        Audit audit = Audit.builder()
                .code("AUD-003")
                .operationEnum(OperationEnum.RECHARGE)
                .etatEnum(EtatEnum.VALID)
                .plateformEnum(PlateformEnum.LOTO)
                .numeroTelephone("70000022")
                .amount(new BigDecimal("700.00"))
                .transactionId("TX-003")
                .eventDate(LocalDateTime.of(2026, 1, 3, 12, 0))
                .build();

        Audit saved = auditRepository.save(audit);

        //assert
        assertNotNull(saved.getId());
        assertEquals("AUD-003", saved.getCode());
        assertEquals(EtatEnum.VALID, saved.getEtatEnum());
    }

    @Test
    void shouldDeleteAudit() {
        Audit audit = Audit.builder()
                .code("AUD-004")
                .operationEnum(OperationEnum.RETRAIT)
                .etatEnum(EtatEnum.EXPIRE)
                .plateformEnum(PlateformEnum.ECD)
                .numeroTelephone("70000023")
                .amount(new BigDecimal("800.00"))
                .transactionId("TX-004")
                .eventDate(LocalDateTime.of(2026, 1, 4, 13, 0))
                .build();

        Audit saved = auditRepository.save(audit);
        auditRepository.delete(saved);

        //assert
        assertFalse(auditRepository.findById(saved.getId()).isPresent());
    }

    @Test
    void shouldUpdateAudit() {
        Audit audit = Audit.builder()
                .code("AUD-005")
                .operationEnum(OperationEnum.RECHARGE)
                .etatEnum(EtatEnum.NON_VALIDE)
                .plateformEnum(PlateformEnum.PMU)
                .numeroTelephone("70000024")
                .amount(new BigDecimal("900.00"))
                .transactionId("TX-005")
                .eventDate(LocalDateTime.of(2026, 1, 5, 14, 0))
                .build();

        Audit saved = auditRepository.save(audit);
        saved.setEtatEnum(EtatEnum.VALID);
        saved.setAmount(new BigDecimal("950.00"));

        Audit updated = auditRepository.save(saved);

        //assert
        assertEquals(EtatEnum.VALID, updated.getEtatEnum());
        assertEquals(new BigDecimal("950.00"), updated.getAmount());
    }
}
