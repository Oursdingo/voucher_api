package com.octopus.voucher.service;

import com.octopus.voucher.dto.request.AccountCreateRequest;
import com.octopus.voucher.dto.request.AccountUpdateRequest;
import com.octopus.voucher.dto.response.AccountResponse;
import com.octopus.voucher.entity.Account;
import com.octopus.voucher.enumeration.PlateformEnum;
import com.octopus.voucher.error.NotFoundException;
import com.octopus.voucher.mapper.AccountMapper;
import com.octopus.voucher.repository.AccountRepository;
import com.octopus.voucher.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
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
class AccountServiceTest {
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AccountMapper accountMapper;
    @InjectMocks
    private AccountService accountService;

    @Test
    void shouldCreateAccount() {
        UUID userId = UUID.randomUUID();
        AccountCreateRequest request = AccountCreateRequest.builder()
                .numeroTelephone("12345678")
                .plateformEnum(PlateformEnum.ECD)
                .userId(userId)
                .build();

        Account entity = Account.builder().id(UUID.randomUUID()).build();
        AccountResponse response = AccountResponse.builder().id(entity.getId()).build();

        when(userRepository.existsById(userId)).thenReturn(true);
        when(accountMapper.toEntity(request)).thenReturn(entity);
        when(accountRepository.save(entity)).thenReturn(entity);
        when(accountMapper.toResponse(entity)).thenReturn(response);

        AccountResponse result = accountService.create(request);

        assertThat(result).isEqualTo(response);
        verify(accountRepository).save(entity);
    }

    @Test
    void shouldThrowOnCreateWhenUserMissing() {
        UUID userId = UUID.randomUUID();
        AccountCreateRequest request = AccountCreateRequest.builder()
                .numeroTelephone("12345678")
                .plateformEnum(PlateformEnum.ECD)
                .userId(userId)
                .build();

        when(userRepository.existsById(userId)).thenReturn(false);

        assertThatThrownBy(() -> accountService.create(request))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("User not found");

        verify(accountRepository, never()).save(any());
    }

    @Test
    void shouldUpdateAccount() {
        UUID id = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        Account entity = Account.builder().id(id).build();
        AccountUpdateRequest request = AccountUpdateRequest.builder()
                .id(id)
                .userId(userId)
                .build();
        AccountResponse response = AccountResponse.builder().id(id).build();

        when(accountRepository.findById(id)).thenReturn(Optional.of(entity));
        when(userRepository.existsById(userId)).thenReturn(true);
        doNothing().when(accountMapper).update(request, entity);
        when(accountRepository.save(entity)).thenReturn(entity);
        when(accountMapper.toResponse(entity)).thenReturn(response);

        AccountResponse result = accountService.update(request);

        assertThat(result).isEqualTo(response);
        verify(accountMapper).update(request, entity);
        verify(accountRepository).save(entity);
    }

    @Test
    void shouldThrowOnUpdateWhenAccountMissing() {
        UUID id = UUID.randomUUID();
        AccountUpdateRequest request = AccountUpdateRequest.builder().id(id).build();

        when(accountRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> accountService.update(request))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Account not found");
    }

    @Test
    void shouldThrowOnUpdateWhenUserMissing() {
        UUID id = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        Account entity = Account.builder().id(id).build();
        AccountUpdateRequest request = AccountUpdateRequest.builder()
                .id(id)
                .userId(userId)
                .build();

        when(accountRepository.findById(id)).thenReturn(Optional.of(entity));
        when(userRepository.existsById(userId)).thenReturn(false);

        assertThatThrownBy(() -> accountService.update(request))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("User not found");

        verify(accountRepository, never()).save(any());
    }

    @Test
    void shouldReturnAccountById() {
        UUID id = UUID.randomUUID();
        Account entity = Account.builder().id(id).build();
        AccountResponse response = AccountResponse.builder().id(id).build();

        when(accountRepository.findById(id)).thenReturn(Optional.of(entity));
        when(accountMapper.toResponse(entity)).thenReturn(response);

        AccountResponse result = accountService.getById(id);

        assertThat(result).isEqualTo(response);
    }

    @Test
    void shouldReturnAllAccounts() {
        Account entity1 = Account.builder().id(UUID.randomUUID()).build();
        Account entity2 = Account.builder().id(UUID.randomUUID()).build();
        AccountResponse response1 = AccountResponse.builder().id(entity1.getId()).build();
        AccountResponse response2 = AccountResponse.builder().id(entity2.getId()).build();

        when(accountRepository.findAll()).thenReturn(List.of(entity1, entity2));
        when(accountMapper.toResponse(entity1)).thenReturn(response1);
        when(accountMapper.toResponse(entity2)).thenReturn(response2);

        List<AccountResponse> result = accountService.getAll();

        assertThat(result).containsExactly(response1, response2);
    }

    @Test
    void shouldDeleteAccount() {
        UUID id = UUID.randomUUID();
        Account entity = Account.builder().id(id).build();

        when(accountRepository.findById(id)).thenReturn(Optional.of(entity));

        accountService.delete(id);

        verify(accountRepository).delete(entity);
    }
}
