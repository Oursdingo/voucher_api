package com.octopus.voucher.service;

import com.octopus.voucher.dto.request.AuditCreateRequest;
import com.octopus.voucher.dto.request.AuditUpdateRequest;
import com.octopus.voucher.dto.response.AuditResponse;
import com.octopus.voucher.entity.Audit;
import com.octopus.voucher.repository.AuditRepository;
import com.octopus.voucher.repository.UserRepository;
import com.octopus.voucher.repository.VoucherRepository;
import com.octopus.voucher.error.NotFoundException;
import com.octopus.voucher.mapper.AuditMapper;
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
        Audit entity = auditMapper.toEntity(request);
        return auditMapper.toResponse(auditRepository.save(entity));
    }

    @Transactional
    public AuditResponse update(AuditUpdateRequest request) {
        Audit entity = auditRepository.findById(request.getId())
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
        Audit entity = auditRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Audit not found"));
        auditRepository.delete(entity);
    }
}
