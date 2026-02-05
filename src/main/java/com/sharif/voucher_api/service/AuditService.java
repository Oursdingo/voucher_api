package com.sharif.voucher_api.service;

import com.sharif.voucher_api.dto.request.AuditCreateRequest;
import com.sharif.voucher_api.dto.request.AuditUpdateRequest;
import com.sharif.voucher_api.dto.response.AuditResponse;
import com.sharif.voucher_api.entityandrepo.audit.AuditEntity;
import com.sharif.voucher_api.entityandrepo.audit.AuditRepository;
import com.sharif.voucher_api.entityandrepo.user.UserRepository;
import com.sharif.voucher_api.entityandrepo.voucher.VoucherRepository;
import com.sharif.voucher_api.error.NotFoundException;
import com.sharif.voucher_api.mapper.AuditMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuditService {
    private final AuditRepository auditRepository;
    private final UserRepository userRepository;
    private final VoucherRepository voucherRepository;
    private final AuditMapper auditMapper;

    @Transactional
    public AuditResponse create(AuditCreateRequest request) {
        if (request.getUserId() != null && !userRepository.existsById(request.getUserId())) {
            throw new NotFoundException("User not found");
        }
        if (request.getVoucherId() != null && !voucherRepository.existsById(request.getVoucherId())) {
            throw new NotFoundException("Voucher not found");
        }
        AuditEntity entity = auditMapper.toEntity(request);
        return auditMapper.toResponse(auditRepository.save(entity));
    }

    @Transactional
    public AuditResponse update(AuditUpdateRequest request) {
        AuditEntity entity = auditRepository.findById(request.getId())
                .orElseThrow(() -> new NotFoundException("Audit not found"));
        auditMapper.update(request, entity);
        return auditMapper.toResponse(auditRepository.save(entity));
    }

    @Transactional(readOnly = true)
    public AuditResponse getById(UUID id) {
        return auditMapper.toResponse(auditRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Audit not found")));
    }

    @Transactional(readOnly = true)
    public List<AuditResponse> getAll() {
        return auditRepository.findAll().stream().map(auditMapper::toResponse).toList();
    }

    @Transactional
    public void delete(UUID id) {
        AuditEntity entity = auditRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Audit not found"));
        auditRepository.delete(entity);
    }
}
