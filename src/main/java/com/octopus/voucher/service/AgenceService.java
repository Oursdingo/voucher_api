package com.octopus.voucher.service;

import com.octopus.voucher.dto.request.AgenceCreateRequest;
import com.octopus.voucher.dto.request.AgenceUpdateRequest;
import com.octopus.voucher.dto.response.AgenceResponse;
import com.octopus.voucher.entity.Agence;
import com.octopus.voucher.repository.AgenceRepository;
import com.octopus.voucher.repository.PointVenteRepository;
import com.octopus.voucher.error.BadRequestException;
import com.octopus.voucher.error.ConflictException;
import com.octopus.voucher.error.NotFoundException;
import com.octopus.voucher.mapper.AgenceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AgenceService {
    private final AgenceRepository agenceRepository;
    private final PointVenteRepository pointVenteRepository;
    private final AgenceMapper agenceMapper;

    @Transactional
    public AgenceResponse create(AgenceCreateRequest request) {
        if (agenceRepository.existsByNom(request.getNom())) {
            throw new ConflictException("Agence name already exists");
        }
        if (agenceRepository.existsByCode(request.getCode())) {
            throw new ConflictException("Agence code already exists");
        }
        validatePointVentesExist(request.getPointVenteIds());
        Agence entity = agenceMapper.toEntity(request);
        Agence saved = agenceRepository.save(entity);
        assignPointVentes(saved, request.getPointVenteIds());
        return agenceMapper.toResponse(saved);
    }

    @Transactional
    public AgenceResponse update(AgenceUpdateRequest request) {
        Agence entity = agenceRepository.findById(request.getId())
                .orElseThrow(() -> new NotFoundException("Agence not found"));

        if (request.getNom() != null && agenceRepository.existsByNomAndIdNot(request.getNom(), entity.getId())) {
            throw new ConflictException("Agence name already exists");
        }
        if (request.getCode() != null && agenceRepository.existsByCodeAndIdNot(request.getCode(), entity.getId())) {
            throw new ConflictException("Agence code already exists");
        }
        if (request.getPointVenteIds() != null) {
            if (request.getPointVenteIds().isEmpty()) {
                throw new BadRequestException("Agence must be associated with at least one point de vente");
            }
            validatePointVentesExist(request.getPointVenteIds());
        }
        agenceMapper.update(request, entity);
        Agence saved = agenceRepository.save(entity);
        if (request.getPointVenteIds() != null) {
            assignPointVentes(saved, request.getPointVenteIds());
        }
        return agenceMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public AgenceResponse getById(UUID id) {
        return agenceMapper.toResponse(agenceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Agence not found")));
    }

    @Transactional(readOnly = true)
    public List<AgenceResponse> getAll() {
        return agenceRepository.findAll().stream().map(agenceMapper::toResponse).toList();
    }

    @Transactional
    public void delete(UUID id) {
        Agence entity = agenceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Agence not found"));
        agenceRepository.delete(entity);
    }

    private void validatePointVentesExist(List<UUID> pointVenteIds) {
        if (pointVenteIds == null || pointVenteIds.isEmpty()) {
            throw new BadRequestException("Agence must be associated with at least one point de vente");
        }
        long count = pointVenteRepository.findAllById(pointVenteIds).stream().count();
        if (count != pointVenteIds.size()) {
            throw new NotFoundException("One or more point de vente not found");
        }
    }

    private void assignPointVentes(Agence agence, List<UUID> pointVenteIds) {
        pointVenteRepository.findAllById(pointVenteIds).forEach(pointVente -> pointVente.setAgence(agence));
    }
}
