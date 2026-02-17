package com.octopus.voucher.service;

import com.octopus.voucher.dto.request.AccountCreateRequest;
import com.octopus.voucher.dto.request.AccountUpdateRequest;
import com.octopus.voucher.dto.response.AccountResponse;
import com.octopus.voucher.entity.Account;
import com.octopus.voucher.repository.AccountRepository;
import com.octopus.voucher.repository.UserRepository;
import com.octopus.voucher.error.NotFoundException;
import com.octopus.voucher.mapper.AccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final AccountMapper accountMapper;

    @Transactional
    public AccountResponse create(AccountCreateRequest request) {
        if (!userRepository.existsById(request.getUserId())) {
            throw new NotFoundException("User not found");
        }
        Account entity = accountMapper.toEntity(request);
        return accountMapper.toResponse(accountRepository.save(entity));
    }

    @Transactional
    public AccountResponse update(AccountUpdateRequest request) {
        Account entity = accountRepository.findById(request.getId())
                .orElseThrow(() -> new NotFoundException("Account not found"));
        if (request.getUserId() != null && !userRepository.existsById(request.getUserId())) {
            throw new NotFoundException("User not found");
        }
        accountMapper.update(request, entity);
        return accountMapper.toResponse(accountRepository.save(entity));
    }

    @Transactional(readOnly = true)
    public AccountResponse getById(UUID id) {
        return accountMapper.toResponse(accountRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Account not found")));
    }

    @Transactional(readOnly = true)
    public List<AccountResponse> getAll() {
        return accountRepository.findAll().stream().map(accountMapper::toResponse).toList();
    }

    @Transactional
    public void delete(UUID id) {
        Account entity = accountRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Account not found"));
        accountRepository.delete(entity);
    }
}
