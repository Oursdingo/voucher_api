package com.octopus.voucher.service;

import com.octopus.voucher.dto.request.PointVenteCreateRequest;
import com.octopus.voucher.dto.request.PointVenteUpdateRequest;
import com.octopus.voucher.dto.response.PointVenteResponse;
import com.octopus.voucher.repository.AgenceRepository;
import com.octopus.voucher.entity.PointVente;
import com.octopus.voucher.repository.PointVenteRepository;
import com.octopus.voucher.error.ConflictException;
import com.octopus.voucher.error.NotFoundException;
import com.octopus.voucher.mapper.PointVenteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PointVenteService {
    private final PointVenteRepository pointVenteRepository;
    private final AgenceRepository agenceRepository;
    private final PointVenteMapper pointVenteMapper;

    @Transactional
    public PointVenteResponse create(PointVenteCreateRequest request) {
        if (pointVenteRepository.existsByName(request.getName())) {
            throw new ConflictException("Point de vente name already exists");
        }
        if (pointVenteRepository.existsByCode(request.getCode())) {
            throw new ConflictException("Point de vente code already exists");
        }
        if (!agenceRepository.existsById(request.getAgenceId())) {
            throw new NotFoundException("Agence not found");
        }
        PointVente entity = pointVenteMapper.toEntity(request);
        return pointVenteMapper.toResponse(pointVenteRepository.save(entity));
    }

    @Transactional
    public PointVenteResponse update(PointVenteUpdateRequest request) {
        PointVente entity = pointVenteRepository.findById(request.getId())
                .orElseThrow(() -> new NotFoundException("Point de vente not found"));

        if (request.getName() != null && pointVenteRepository.existsByNameAndIdNot(request.getName(), entity.getId())) {
            throw new ConflictException("Point de vente name already exists");
        }
        if (request.getCode() != null && pointVenteRepository.existsByCodeAndIdNot(request.getCode(), entity.getId())) {
            throw new ConflictException("Point de vente code already exists");
        }
        if (request.getAgenceId() != null && !agenceRepository.existsById(request.getAgenceId())) {
            throw new NotFoundException("Agence not found");
        }
        pointVenteMapper.update(request, entity);
        return pointVenteMapper.toResponse(pointVenteRepository.save(entity));
    }

    @Transactional(readOnly = true)
    public PointVenteResponse getById(UUID id) {
        return pointVenteMapper.toResponse(pointVenteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Point de vente not found")));
    }

    @Transactional(readOnly = true)
    public List<PointVenteResponse> getAll() {
        return pointVenteRepository.findAll().stream().map(pointVenteMapper::toResponse).toList();
    }

    @Transactional
    public void delete(UUID id) {
        PointVente entity = pointVenteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Point de vente not found"));
        pointVenteRepository.delete(entity);
    }
}
