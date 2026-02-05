package com.sharif.voucher_api.service;

import com.sharif.voucher_api.dto.request.VoucherCreateRequest;
import com.sharif.voucher_api.dto.request.VoucherUpdateRequest;
import com.sharif.voucher_api.dto.response.VoucherResponse;
import com.sharif.voucher_api.entityandrepo.account.AccountEntity;
import com.sharif.voucher_api.entityandrepo.account.AccountRepository;
import com.sharif.voucher_api.entityandrepo.voucher.VoucherEntity;
import com.sharif.voucher_api.entityandrepo.voucher.VoucherRepository;
import com.sharif.voucher_api.enumeration.Etat;
import com.sharif.voucher_api.enumeration.Statut;
import com.sharif.voucher_api.error.NotFoundException;
import com.sharif.voucher_api.mapper.VoucherMapper;
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

    @Transactional
    public VoucherResponse create(VoucherCreateRequest request) {
        AccountEntity account = resolveAccount(request);
        VoucherEntity entity = voucherMapper.toEntity(request);
        entity.setAccount(account);
        entity.setCode(generateCode());
        entity.setEtat(Etat.VALID);
        return voucherMapper.toResponse(voucherRepository.save(entity));
    }

    @Transactional
    public VoucherResponse update(VoucherUpdateRequest request) {
        VoucherEntity entity = voucherRepository.findById(request.getId())
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
        VoucherEntity entity = voucherRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Voucher not found"));
        voucherRepository.delete(entity);
    }

    private AccountEntity resolveAccount(VoucherCreateRequest request) {
        if (request.getAccountId() != null) {
            return accountRepository.findById(request.getAccountId())
                    .orElseThrow(() -> new NotFoundException("Account not found"));
        }
        return accountRepository.findByNumeroTelephoneAndPlateform(request.getNumeroTelephone(), request.getPlateform())
                .orElseGet(() -> {
                    AccountEntity account = new AccountEntity();
                    account.setNumeroTelephone(request.getNumeroTelephone());
                    account.setPlateform(request.getPlateform());
                    account.setStatut(Statut.ACTIVE);
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
