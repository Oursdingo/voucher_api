package com.octopus.voucher.service;

import com.octopus.voucher.dto.request.RegionalCreateRequest;
import com.octopus.voucher.dto.request.RegionalUpdateRequest;
import com.octopus.voucher.dto.response.RegionalResponse;
import com.octopus.voucher.entity.Regional;
import com.octopus.voucher.repository.RegionalRepository;
import com.octopus.voucher.error.NotFoundException;
import com.octopus.voucher.mapper.RegionalMapper;
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
        Regional entity = regionalMapper.toEntity(request);
        return regionalMapper.toResponse(regionalRepository.save(entity));
    }

    @Transactional
    public RegionalResponse update(RegionalUpdateRequest request) {
        Regional entity = regionalRepository.findById(request.getId())
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
        Regional entity = regionalRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Regional not found"));
        regionalRepository.delete(entity);
    }
}
