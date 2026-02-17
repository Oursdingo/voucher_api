package com.octopus.voucher.service;

import com.octopus.voucher.dto.request.VoucherCreateRequest;
import com.octopus.voucher.dto.request.VoucherUpdateRequest;
import com.octopus.voucher.dto.response.VoucherResponse;
import com.octopus.voucher.entity.Account;
import com.octopus.voucher.repository.AccountRepository;
import com.octopus.voucher.entity.Voucher;
import com.octopus.voucher.repository.VoucherRepository;
import com.octopus.voucher.enumeration.EtatEnum;
import com.octopus.voucher.enumeration.StatutEnum;
import com.octopus.voucher.error.NotFoundException;
import com.octopus.voucher.mapper.VoucherMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VoucherService {
    private static final String CODE_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 10;

    private final VoucherRepository voucherRepository;
    private final AccountRepository accountRepository;
    private final VoucherMapper voucherMapper;
    private final SecureRandom secureRandom = new SecureRandom();
    //TODO 1: refactor
    @Transactional
    public VoucherResponse create(VoucherCreateRequest request) {
        Account account = resolveAccount(request);
        Voucher entity = voucherMapper.toEntity(request);
        entity.setAccount(account);
        entity.setCode(generateCode());
        entity.setEtatEnum(EtatEnum.VALID);
        return voucherMapper.toResponse(voucherRepository.save(entity));
    }

    @Transactional
    public VoucherResponse update(VoucherUpdateRequest request) {
        Voucher entity = voucherRepository.findById(request.getId())
                .orElseThrow(() -> new NotFoundException("Voucher not found"));
        voucherMapper.update(request, entity);
        return voucherMapper.toResponse(voucherRepository.save(entity));
    }

    @Transactional(readOnly = true)
    public VoucherResponse getById(UUID id) {
        return voucherMapper.toResponse(voucherRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Voucher not found")));
    }

    @Transactional(readOnly = true)
    public List<VoucherResponse> getAll() {
        return voucherRepository.findAll().stream().map(voucherMapper::toResponse).toList();
    }

    @Transactional
    public void delete(UUID id) {
        Voucher entity = voucherRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Voucher not found"));
        voucherRepository.delete(entity);
    }

    private Account resolveAccount(VoucherCreateRequest request) {
        if (request.getAccountId() != null) {
            return accountRepository.findById(request.getAccountId())
                    .orElseThrow(() -> new NotFoundException("Account not found"));
        }
        return accountRepository.findByNumeroTelephoneAndPlateform(request.getNumeroTelephone(), request.getPlateformEnum())
                .orElseGet(() -> {
                    Account account = new Account();
                    account.setNumeroTelephone(request.getNumeroTelephone());
                    account.setPlateform(request.getPlateformEnum());
                    account.setStatutEnum(StatutEnum.ACTIVE);
                    account.setBalance(BigDecimal.ZERO);
                    return accountRepository.save(account);
                });
    }

    private String generateCode() {
        StringBuilder sb = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            sb.append(CODE_CHARS.charAt(secureRandom.nextInt(CODE_CHARS.length())));
        }
        return sb.toString();
    }
}
