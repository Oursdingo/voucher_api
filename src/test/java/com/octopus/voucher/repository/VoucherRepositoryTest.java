package com.octopus.voucher.repository;

import com.octopus.voucher.entity.Voucher;
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
class VoucherRepositoryTest {
    @Autowired
    private VoucherRepository voucherRepository;

    @Test
    void shouldGetAllVouchers() {
        Voucher v1 = Voucher.builder()
                .code("VCH-001")
                .amount(new BigDecimal("100.00"))
                .operationEnum(OperationEnum.RECHARGE)
                .etatEnum(EtatEnum.VALID)
                .plateformEnum(PlateformEnum.ECD)
                .numeroTelephone("70000030")
                .transactionId("TRX-001")
                .validationDate(LocalDateTime.of(2026, 1, 1, 8, 0))
                .expirationDate(LocalDateTime.of(2026, 2, 1, 8, 0))
                .build();

        Voucher v2 = Voucher.builder()
                .code("VCH-002")
                .amount(new BigDecimal("200.00"))
                .operationEnum(OperationEnum.RETRAIT)
                .etatEnum(EtatEnum.NON_VALIDE)
                .plateformEnum(PlateformEnum.PMU)
                .numeroTelephone("70000031")
                .transactionId("TRX-002")
                .validationDate(LocalDateTime.of(2026, 1, 2, 8, 0))
                .expirationDate(LocalDateTime.of(2026, 2, 2, 8, 0))
                .build();

        voucherRepository.saveAll(List.of(v1, v2));

        List<Voucher> vouchers = voucherRepository.findAll();

        //assert
        assertEquals(2, vouchers.size());
        assertTrue(vouchers.stream().anyMatch(v -> "VCH-001".equals(v.getCode())));
    }

    @Test
    void shouldSaveVoucher() {
        Voucher voucher = Voucher.builder()
                .code("VCH-003")
                .amount(new BigDecimal("300.00"))
                .operationEnum(OperationEnum.RECHARGE)
                .etatEnum(EtatEnum.VALID)
                .plateformEnum(PlateformEnum.LOTO)
                .numeroTelephone("70000032")
                .transactionId("TRX-003")
                .validationDate(LocalDateTime.of(2026, 1, 3, 8, 0))
                .expirationDate(LocalDateTime.of(2026, 2, 3, 8, 0))
                .build();

        Voucher saved = voucherRepository.save(voucher);

        //assert
        assertNotNull(saved.getId());
        assertEquals("VCH-003", saved.getCode());
        assertEquals(EtatEnum.VALID, saved.getEtatEnum());
    }

    @Test
    void shouldDeleteVoucher() {
        Voucher voucher = Voucher.builder()
                .code("VCH-004")
                .amount(new BigDecimal("400.00"))
                .operationEnum(OperationEnum.RETRAIT)
                .etatEnum(EtatEnum.EXPIRE)
                .plateformEnum(PlateformEnum.ECD)
                .numeroTelephone("70000033")
                .transactionId("TRX-004")
                .validationDate(LocalDateTime.of(2026, 1, 4, 8, 0))
                .expirationDate(LocalDateTime.of(2026, 2, 4, 8, 0))
                .build();

        Voucher saved = voucherRepository.save(voucher);
        voucherRepository.delete(saved);

        //assert
        assertFalse(voucherRepository.findById(saved.getId()).isPresent());
    }

    @Test
    void shouldUpdateVoucher() {
        Voucher voucher = Voucher.builder()
                .code("VCH-005")
                .amount(new BigDecimal("500.00"))
                .operationEnum(OperationEnum.RECHARGE)
                .etatEnum(EtatEnum.NON_VALIDE)
                .plateformEnum(PlateformEnum.PMU)
                .numeroTelephone("70000034")
                .transactionId("TRX-005")
                .validationDate(LocalDateTime.of(2026, 1, 5, 8, 0))
                .expirationDate(LocalDateTime.of(2026, 2, 5, 8, 0))
                .build();

        Voucher saved = voucherRepository.save(voucher);
        saved.setEtatEnum(EtatEnum.VALID);
        saved.setAmount(new BigDecimal("550.00"));

        Voucher updated = voucherRepository.save(saved);

        //assert
        assertEquals(EtatEnum.VALID, updated.getEtatEnum());
        assertEquals(new BigDecimal("550.00"), updated.getAmount());
    }
}
