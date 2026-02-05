package com.sharif.voucher_api.service;

import com.sharif.voucher_api.dto.request.RegionalCreateRequest;
import com.sharif.voucher_api.dto.request.RegionalUpdateRequest;
import com.sharif.voucher_api.dto.response.RegionalResponse;
import com.sharif.voucher_api.entityandrepo.regionalManagement.RegionalEntity;
import com.sharif.voucher_api.entityandrepo.regionalManagement.RegionalRepository;
import com.sharif.voucher_api.error.NotFoundException;
import com.sharif.voucher_api.mapper.RegionalMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegionalService {
    private final RegionalRepository regionalRepository;
    private final RegionalMapper regionalMapper;

    @Transactional
    public RegionalResponse create(RegionalCreateRequest request) {
        RegionalEntity entity = regionalMapper.toEntity(request);
        return regionalMapper.toResponse(regionalRepository.save(entity));
    }

    @Transactional
    public RegionalResponse update(RegionalUpdateRequest request) {
        RegionalEntity entity = regionalRepository.findById(request.getId())
                .orElseThrow(() -> new NotFoundException("Regional not found"));
        regionalMapper.update(request, entity);
        return regionalMapper.toResponse(regionalRepository.save(entity));
    }

    @Transactional(readOnly = true)
    public RegionalResponse getById(UUID id) {
        return regionalMapper.toResponse(regionalRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Regional not found")));
    }

    @Transactional(readOnly = true)
    public List<RegionalResponse> getAll() {
        return regionalRepository.findAll().stream().map(regionalMapper::toResponse).toList();
    }

    @Transactional
    public void delete(UUID id) {
        RegionalEntity entity = regionalRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Regional not found"));
        regionalRepository.delete(entity);
    }
}
