package com.octopus.voucher.service;

import com.octopus.voucher.dto.request.PointVenteCreateRequest;
import com.octopus.voucher.dto.request.PointVenteUpdateRequest;
import com.octopus.voucher.dto.response.PointVenteResponse;
import com.octopus.voucher.entity.PointVente;
import com.octopus.voucher.enumeration.TypePdvEnum;
import com.octopus.voucher.error.ConflictException;
import com.octopus.voucher.error.NotFoundException;
import com.octopus.voucher.mapper.PointVenteMapper;
import com.octopus.voucher.repository.AgenceRepository;
import com.octopus.voucher.repository.PointVenteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PointVenteServiceTest {
    @Mock
    private PointVenteRepository pointVenteRepository;
    @Mock
    private AgenceRepository agenceRepository;
    @Mock
    private PointVenteMapper pointVenteMapper;
    @InjectMocks
    private PointVenteService pointVenteService;

    @Test
    void shouldThrowOnCreateWhenNameExists() {
        PointVenteCreateRequest request = PointVenteCreateRequest.builder()
                .name("PV A")
                .code("PV001")
                .salesPointType(TypePdvEnum.ECD)
                .agenceId(UUID.randomUUID())
                .build();

        when(pointVenteRepository.existsByName("PV A")).thenReturn(true);

        assertThatThrownBy(() -> pointVenteService.create(request))
                .isInstanceOf(ConflictException.class)
                .hasMessage("Point de vente name already exists");
    }

    @Test
    void shouldThrowOnCreateWhenCodeExists() {
        PointVenteCreateRequest request = PointVenteCreateRequest.builder()
                .name("PV A")
                .code("PV001")
                .salesPointType(TypePdvEnum.ECD)
                .agenceId(UUID.randomUUID())
                .build();

        when(pointVenteRepository.existsByName("PV A")).thenReturn(false);
        when(pointVenteRepository.existsByCode("PV001")).thenReturn(true);

        assertThatThrownBy(() -> pointVenteService.create(request))
                .isInstanceOf(ConflictException.class)
                .hasMessage("Point de vente code already exists");
    }

    @Test
    void shouldThrowOnCreateWhenAgenceMissing() {
        UUID agenceId = UUID.randomUUID();
        PointVenteCreateRequest request = PointVenteCreateRequest.builder()
                .name("PV A")
                .code("PV001")
                .salesPointType(TypePdvEnum.ECD)
                .agenceId(agenceId)
                .build();

        when(pointVenteRepository.existsByName("PV A")).thenReturn(false);
        when(pointVenteRepository.existsByCode("PV001")).thenReturn(false);
        when(agenceRepository.existsById(agenceId)).thenReturn(false);

        assertThatThrownBy(() -> pointVenteService.create(request))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Agence not found");

        verify(pointVenteRepository, never()).save(any());
    }

    @Test
    void shouldCreatePointVente() {
        UUID agenceId = UUID.randomUUID();
        PointVenteCreateRequest request = PointVenteCreateRequest.builder()
                .name("PV A")
                .code("PV001")
                .salesPointType(TypePdvEnum.ECD)
                .agenceId(agenceId)
                .build();

        PointVente entity = PointVente.builder().id(UUID.randomUUID()).build();
        PointVenteResponse response = PointVenteResponse.builder().id(entity.getId()).build();

        when(pointVenteRepository.existsByName("PV A")).thenReturn(false);
        when(pointVenteRepository.existsByCode("PV001")).thenReturn(false);
        when(agenceRepository.existsById(agenceId)).thenReturn(true);
        when(pointVenteMapper.toEntity(request)).thenReturn(entity);
        when(pointVenteRepository.save(entity)).thenReturn(entity);
        when(pointVenteMapper.toResponse(entity)).thenReturn(response);

        PointVenteResponse result = pointVenteService.create(request);

        assertThat(result).isEqualTo(response);
    }

    @Test
    void shouldThrowOnUpdateWhenPointVenteMissing() {
        UUID id = UUID.randomUUID();
        PointVenteUpdateRequest request = PointVenteUpdateRequest.builder().id(id).build();

        when(pointVenteRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> pointVenteService.update(request))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Point de vente not found");
    }

    @Test
    void shouldThrowOnUpdateWhenNameExists() {
        UUID id = UUID.randomUUID();
        PointVente entity = PointVente.builder().id(id).build();
        PointVenteUpdateRequest request = PointVenteUpdateRequest.builder()
                .id(id)
                .name("PV A")
                .build();

        when(pointVenteRepository.findById(id)).thenReturn(Optional.of(entity));
        when(pointVenteRepository.existsByNameAndIdNot("PV A", id)).thenReturn(true);

        assertThatThrownBy(() -> pointVenteService.update(request))
                .isInstanceOf(ConflictException.class)
                .hasMessage("Point de vente name already exists");
    }

    @Test
    void shouldThrowOnUpdateWhenCodeExists() {
        UUID id = UUID.randomUUID();
        PointVente entity = PointVente.builder().id(id).build();
        PointVenteUpdateRequest request = PointVenteUpdateRequest.builder()
                .id(id)
                .code("PV001")
                .build();

        when(pointVenteRepository.findById(id)).thenReturn(Optional.of(entity));
        when(pointVenteRepository.existsByCodeAndIdNot("PV001", id)).thenReturn(true);

        assertThatThrownBy(() -> pointVenteService.update(request))
                .isInstanceOf(ConflictException.class)
                .hasMessage("Point de vente code already exists");
    }

    @Test
    void shouldThrowOnUpdateWhenAgenceMissing() {
        UUID id = UUID.randomUUID();
        UUID agenceId = UUID.randomUUID();
        PointVente entity = PointVente.builder().id(id).build();
        PointVenteUpdateRequest request = PointVenteUpdateRequest.builder()
                .id(id)
                .agenceId(agenceId)
                .build();

        when(pointVenteRepository.findById(id)).thenReturn(Optional.of(entity));
        when(agenceRepository.existsById(agenceId)).thenReturn(false);

        assertThatThrownBy(() -> pointVenteService.update(request))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Agence not found");
    }

    @Test
    void shouldUpdatePointVente() {
        UUID id = UUID.randomUUID();
        PointVente entity = PointVente.builder().id(id).build();
        PointVenteUpdateRequest request = PointVenteUpdateRequest.builder()
                .id(id)
                .name("PV B")
                .build();
        PointVenteResponse response = PointVenteResponse.builder().id(id).build();

        when(pointVenteRepository.findById(id)).thenReturn(Optional.of(entity));
        when(pointVenteRepository.existsByNameAndIdNot("PV B", id)).thenReturn(false);
        doNothing().when(pointVenteMapper).update(request, entity);
        when(pointVenteRepository.save(entity)).thenReturn(entity);
        when(pointVenteMapper.toResponse(entity)).thenReturn(response);

        PointVenteResponse result = pointVenteService.update(request);

        assertThat(result).isEqualTo(response);
        verify(pointVenteMapper).update(request, entity);
    }
}
