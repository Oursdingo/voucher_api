package com.octopus.voucher.repository;

import com.octopus.voucher.entity.Account;
import com.octopus.voucher.enumeration.PlateformEnum;
import com.octopus.voucher.enumeration.StatutEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AccountRepositoryTest {
    @Autowired
    private AccountRepository accountRepository;

    @Test
    void shouldGetAllAccounts() {
        Account account1 = Account.builder()
                .numeroTelephone("70000010")
                .statutEnum(StatutEnum.ACTIVE)
                .observation("Creation de compte ECD")
                .balance(new BigDecimal("100.00"))
                .plateform(PlateformEnum.ECD)
                .build();

        Account account2 = Account.builder()
                .numeroTelephone("70000011")
                .statutEnum(StatutEnum.DESACTIVE)
                .observation("Creation de compte PMU ")
                .balance(new BigDecimal("200.00"))
                .plateform(PlateformEnum.PMU)
                .build();

        accountRepository.saveAll(List.of(account1, account2));

        List<Account> accounts = accountRepository.findAll();

        //assert
        assertEquals(2, accounts.size());
        assertTrue(accounts.stream().anyMatch(a -> "70000010".equals(a.getNumeroTelephone())));
    }

    @Test
    void shouldFindByNumeroTelephoneAndPlateform() {
        Account account = Account.builder()
                .numeroTelephone("70000012")
                .statutEnum(StatutEnum.ACTIVE)
                .balance(new BigDecimal("150.00"))
                .plateform(PlateformEnum.LOTO)
                .build();

        accountRepository.save(account);

        Optional<Account> found = accountRepository.findByNumeroTelephoneAndPlateform("70000012", PlateformEnum.LOTO);

        //assert
        assertTrue(found.isPresent());
        assertEquals(PlateformEnum.LOTO, found.get().getPlateform());
        assertEquals("70000012", found.get().getNumeroTelephone());
    }

    @Test
    void shouldSaveAccount() {
        Account account = Account.builder()
                .numeroTelephone("70000013")
                .statutEnum(StatutEnum.ACTIVE)
                .observation("ok")
                .balance(new BigDecimal("300.00"))
                .plateform(PlateformEnum.ECD)
                .build();

        Account savedAccount = accountRepository.save(account);

        //assert
        assertNotNull(savedAccount.getId());
        assertEquals("70000013", savedAccount.getNumeroTelephone());
        assertEquals(StatutEnum.ACTIVE, savedAccount.getStatutEnum());
        assertEquals(PlateformEnum.ECD, savedAccount.getPlateform());
    }

    @Test
    void shouldDeleteAccount() {
        Account account = Account.builder()
                .numeroTelephone("70000014")
                .statutEnum(StatutEnum.ACTIVE)
                .balance(new BigDecimal("400.00"))
                .plateform(PlateformEnum.PMU)
                .build();

        Account saved = accountRepository.save(account);

        accountRepository.delete(saved);

        //assert
        assertFalse(accountRepository.findById(saved.getId()).isPresent());
    }

    @Test
    void shouldUpdateAccount() {
        Account account = Account.builder()
                .numeroTelephone("70000015")
                .statutEnum(StatutEnum.ACTIVE)
                .balance(new BigDecimal("500.00"))
                .plateform(PlateformEnum.ECD)
                .build();

        Account saved = accountRepository.save(account);
        saved.setStatutEnum(StatutEnum.DESACTIVE);
        saved.setBalance(new BigDecimal("450.00"));

        Account updated = accountRepository.save(saved);

        //assert
        assertEquals(StatutEnum.DESACTIVE, updated.getStatutEnum());
        assertEquals(new BigDecimal("450.00"), updated.getBalance());
    }
}
