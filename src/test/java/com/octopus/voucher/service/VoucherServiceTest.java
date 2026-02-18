package com.octopus.voucher.service;

import com.octopus.voucher.dto.request.VoucherCreateRequest;
import com.octopus.voucher.dto.request.VoucherUpdateRequest;
import com.octopus.voucher.dto.response.VoucherResponse;
import com.octopus.voucher.entity.Account;
import com.octopus.voucher.entity.Voucher;
import com.octopus.voucher.enumeration.EtatEnum;
import com.octopus.voucher.enumeration.OperationEnum;
import com.octopus.voucher.enumeration.PlateformEnum;
import com.octopus.voucher.enumeration.StatutEnum;
import com.octopus.voucher.error.NotFoundException;
import com.octopus.voucher.mapper.VoucherMapper;
import com.octopus.voucher.repository.AccountRepository;
import com.octopus.voucher.repository.VoucherRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VoucherServiceTest {
    @Mock
    private VoucherRepository voucherRepository;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private VoucherMapper voucherMapper;
    @InjectMocks
    private VoucherService voucherService;

    @Test
    void shouldThrowOnCreateWhenAccountIdMissing() {
        UUID accountId = UUID.randomUUID();
        VoucherCreateRequest request = VoucherCreateRequest.builder()
                .numeroTelephone("12345678")
                .amount(BigDecimal.valueOf(100))
                .plateformEnum(PlateformEnum.ECD)
                .operationEnum(OperationEnum.RECHARGE)
                .accountId(accountId)
                .expirationDate(LocalDateTime.now().plusDays(1))
                .build();

        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> voucherService.create(request))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Account not found");
    }

    @Test
    void shouldCreateVoucherWithProvidedAccount() {
        UUID accountId = UUID.randomUUID();
        Account account = Account.builder().id(accountId).build();
        VoucherCreateRequest request = VoucherCreateRequest.builder()
                .numeroTelephone("12345678")
                .amount(BigDecimal.valueOf(100))
                .plateformEnum(PlateformEnum.ECD)
                .operationEnum(OperationEnum.RECHARGE)
                .accountId(accountId)
                .expirationDate(LocalDateTime.now().plusDays(1))
                .build();

        Voucher entity = new Voucher();
        VoucherResponse response = VoucherResponse.builder().build();

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(voucherMapper.toEntity(request)).thenReturn(entity);
        when(voucherRepository.save(any(Voucher.class))).thenAnswer(inv -> inv.getArgument(0));
        when(voucherMapper.toResponse(any(Voucher.class))).thenReturn(response);

        VoucherResponse result = voucherService.create(request);

        ArgumentCaptor<Voucher> captor = ArgumentCaptor.forClass(Voucher.class);
        verify(voucherRepository).save(captor.capture());
        Voucher saved = captor.getValue();

        assertThat(result).isEqualTo(response);
        assertThat(saved.getAccount()).isEqualTo(account);
        assertThat(saved.getEtatEnum()).isEqualTo(EtatEnum.VALID);
        assertThat(saved.getCode()).matches("[A-Z0-9]{10}");
    }

    @Test
    void shouldCreateAccountWhenMissingByPhoneAndPlateform() {
        VoucherCreateRequest request = VoucherCreateRequest.builder()
                .numeroTelephone("12345678")
                .amount(BigDecimal.valueOf(150))
                .plateformEnum(PlateformEnum.PMU)
                .operationEnum(OperationEnum.RECHARGE)
                .expirationDate(LocalDateTime.now().plusDays(1))
                .build();

        Voucher entity = new Voucher();
        VoucherResponse response = VoucherResponse.builder().build();

        when(accountRepository.findByNumeroTelephoneAndPlateform("12345678", PlateformEnum.PMU))
                .thenReturn(Optional.empty());
        when(accountRepository.save(any(Account.class))).thenAnswer(inv -> {
            Account acc = inv.getArgument(0);
            acc.setId(UUID.randomUUID());
            return acc;
        });
        when(voucherMapper.toEntity(request)).thenReturn(entity);
        when(voucherRepository.save(any(Voucher.class))).thenAnswer(inv -> inv.getArgument(0));
        when(voucherMapper.toResponse(any(Voucher.class))).thenReturn(response);

        VoucherResponse result = voucherService.create(request);

        ArgumentCaptor<Account> accountCaptor = ArgumentCaptor.forClass(Account.class);
        verify(accountRepository).save(accountCaptor.capture());
        Account savedAccount = accountCaptor.getValue();

        ArgumentCaptor<Voucher> voucherCaptor = ArgumentCaptor.forClass(Voucher.class);
        verify(voucherRepository).save(voucherCaptor.capture());
        Voucher savedVoucher = voucherCaptor.getValue();

        assertThat(result).isEqualTo(response);
        assertThat(savedAccount.getNumeroTelephone()).isEqualTo("12345678");
        assertThat(savedAccount.getPlateform()).isEqualTo(PlateformEnum.PMU);
        assertThat(savedAccount.getStatutEnum()).isEqualTo(StatutEnum.ACTIVE);
        assertThat(savedAccount.getBalance()).isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(savedVoucher.getAccount()).isEqualTo(savedAccount);
    }

    @Test
    void shouldUseExistingAccountByPhoneAndPlateform() {
        Account existing = Account.builder().id(UUID.randomUUID()).build();
        VoucherCreateRequest request = VoucherCreateRequest.builder()
                .numeroTelephone("12345678")
                .amount(BigDecimal.valueOf(200))
                .plateformEnum(PlateformEnum.LOTO)
                .operationEnum(OperationEnum.RECHARGE)
                .expirationDate(LocalDateTime.now().plusDays(1))
                .build();

        Voucher entity = new Voucher();
        VoucherResponse response = VoucherResponse.builder().build();

        when(accountRepository.findByNumeroTelephoneAndPlateform("12345678", PlateformEnum.LOTO))
                .thenReturn(Optional.of(existing));
        when(voucherMapper.toEntity(request)).thenReturn(entity);
        when(voucherRepository.save(any(Voucher.class))).thenAnswer(inv -> inv.getArgument(0));
        when(voucherMapper.toResponse(any(Voucher.class))).thenReturn(response);

        VoucherResponse result = voucherService.create(request);

        ArgumentCaptor<Voucher> voucherCaptor = ArgumentCaptor.forClass(Voucher.class);
        verify(voucherRepository).save(voucherCaptor.capture());
        Voucher savedVoucher = voucherCaptor.getValue();

        assertThat(result).isEqualTo(response);
        assertThat(savedVoucher.getAccount()).isEqualTo(existing);
        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    void shouldThrowOnUpdateWhenVoucherMissing() {
        UUID id = UUID.randomUUID();
        VoucherUpdateRequest request = VoucherUpdateRequest.builder().id(id).build();

        when(voucherRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> voucherService.update(request))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Voucher not found");
    }

    @Test
    void shouldUpdateVoucher() {
        UUID id = UUID.randomUUID();
        Voucher entity = Voucher.builder().id(id).build();
        VoucherUpdateRequest request = VoucherUpdateRequest.builder()
                .id(id)
                .etatEnum(EtatEnum.NON_VALIDE)
                .build();
        VoucherResponse response = VoucherResponse.builder().id(id).build();

        when(voucherRepository.findById(id)).thenReturn(Optional.of(entity));
        doNothing().when(voucherMapper).update(request, entity);
        when(voucherRepository.save(entity)).thenReturn(entity);
        when(voucherMapper.toResponse(entity)).thenReturn(response);

        VoucherResponse result = voucherService.update(request);

        assertThat(result).isEqualTo(response);
        verify(voucherMapper).update(request, entity);
    }
}
