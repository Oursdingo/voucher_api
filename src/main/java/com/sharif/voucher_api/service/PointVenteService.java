package com.sharif.voucher_api.service;

import com.sharif.voucher_api.dto.request.PointVenteCreateRequest;
import com.sharif.voucher_api.dto.request.PointVenteUpdateRequest;
import com.sharif.voucher_api.dto.response.PointVenteResponse;
import com.sharif.voucher_api.entityandrepo.agence.AgenceRepository;
import com.sharif.voucher_api.entityandrepo.pointVente.PointVenteEntity;
import com.sharif.voucher_api.entityandrepo.pointVente.PointVenteRepository;
import com.sharif.voucher_api.error.ConflictException;
import com.sharif.voucher_api.error.NotFoundException;
import com.sharif.voucher_api.mapper.PointVenteMapper;
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
        PointVenteEntity entity = pointVenteMapper.toEntity(request);
        return pointVenteMapper.toResponse(pointVenteRepository.save(entity));
    }

    @Transactional
    public PointVenteResponse update(PointVenteUpdateRequest request) {
        PointVenteEntity entity = pointVenteRepository.findById(request.getId())
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
        PointVenteEntity entity = pointVenteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Point de vente not found"));
        pointVenteRepository.delete(entity);
    }
}
