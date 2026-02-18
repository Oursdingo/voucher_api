package com.octopus.voucher.service;

import com.octopus.voucher.dto.request.AgenceCreateRequest;
import com.octopus.voucher.dto.request.AgenceUpdateRequest;
import com.octopus.voucher.dto.response.AgenceResponse;
import com.octopus.voucher.entity.Agence;
import com.octopus.voucher.entity.PointVente;
import com.octopus.voucher.error.BadRequestException;
import com.octopus.voucher.error.ConflictException;
import com.octopus.voucher.error.NotFoundException;
import com.octopus.voucher.mapper.AgenceMapper;
import com.octopus.voucher.repository.AgenceRepository;
import com.octopus.voucher.repository.PointVenteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AgenceServiceTest {
    @Mock
    private AgenceRepository agenceRepository;
    @Mock
    private PointVenteRepository pointVenteRepository;
    @Mock
    private AgenceMapper agenceMapper;
    @InjectMocks
    private AgenceService agenceService;

    @Test
    void shouldThrowOnCreateWhenNameExists() {
        AgenceCreateRequest request = AgenceCreateRequest.builder()
                .nom("Agence A")
                .code("AG001")
                .pointVenteIds(List.of(UUID.randomUUID()))
                .build();

        when(agenceRepository.existsByNom("Agence A")).thenReturn(true);

        assertThatThrownBy(() -> agenceService.create(request))
                .isInstanceOf(ConflictException.class)
                .hasMessage("Agence name already exists");

        verify(agenceRepository, never()).save(any());
    }

    @Test
    void shouldThrowOnCreateWhenCodeExists() {
        AgenceCreateRequest request = AgenceCreateRequest.builder()
                .nom("Agence A")
                .code("AG001")
                .pointVenteIds(List.of(UUID.randomUUID()))
                .build();

        when(agenceRepository.existsByNom("Agence A")).thenReturn(false);
        when(agenceRepository.existsByCode("AG001")).thenReturn(true);

        assertThatThrownBy(() -> agenceService.create(request))
                .isInstanceOf(ConflictException.class)
                .hasMessage("Agence code already exists");

        verify(agenceRepository, never()).save(any());
    }

    @Test
    void shouldThrowOnCreateWhenPointVentesMissing() {
        AgenceCreateRequest request = AgenceCreateRequest.builder()
                .nom("Agence A")
                .code("AG001")
                .pointVenteIds(List.of())
                .build();

        when(agenceRepository.existsByNom("Agence A")).thenReturn(false);
        when(agenceRepository.existsByCode("AG001")).thenReturn(false);

        assertThatThrownBy(() -> agenceService.create(request))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Agence must be associated with at least one point de vente");
    }

    @Test
    void shouldThrowOnCreateWhenPointVentesNotFound() {
        List<UUID> ids = List.of(UUID.randomUUID(), UUID.randomUUID());
        AgenceCreateRequest request = AgenceCreateRequest.builder()
                .nom("Agence A")
                .code("AG001")
                .pointVenteIds(ids)
                .build();

        when(agenceRepository.existsByNom("Agence A")).thenReturn(false);
        when(agenceRepository.existsByCode("AG001")).thenReturn(false);
        when(pointVenteRepository.findAllById(ids)).thenReturn(List.of(mock(PointVente.class)));

        assertThatThrownBy(() -> agenceService.create(request))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("One or more point de vente not found");
    }

    @Test
    void shouldCreateAgenceAndAssignPointVentes() {
        UUID pv1 = UUID.randomUUID();
        UUID pv2 = UUID.randomUUID();
        List<UUID> ids = List.of(pv1, pv2);
        AgenceCreateRequest request = AgenceCreateRequest.builder()
                .nom("Agence A")
                .code("AG001")
                .pointVenteIds(ids)
                .build();

        PointVente pointVente1 = mock(PointVente.class);
        PointVente pointVente2 = mock(PointVente.class);
        Agence entity = Agence.builder().id(UUID.randomUUID()).build();
        AgenceResponse response = AgenceResponse.builder().id(entity.getId()).build();

        when(agenceRepository.existsByNom("Agence A")).thenReturn(false);
        when(agenceRepository.existsByCode("AG001")).thenReturn(false);
        when(pointVenteRepository.findAllById(ids)).thenReturn(List.of(pointVente1, pointVente2));
        when(agenceMapper.toEntity(request)).thenReturn(entity);
        when(agenceRepository.save(entity)).thenReturn(entity);
        when(agenceMapper.toResponse(entity)).thenReturn(response);

        AgenceResponse result = agenceService.create(request);

        assertThat(result).isEqualTo(response);
        verify(pointVente1).setAgence(entity);
        verify(pointVente2).setAgence(entity);
    }

    @Test
    void shouldThrowOnUpdateWhenAgenceMissing() {
        UUID id = UUID.randomUUID();
        AgenceUpdateRequest request = AgenceUpdateRequest.builder().id(id).build();

        when(agenceRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> agenceService.update(request))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Agence not found");
    }

    @Test
    void shouldThrowOnUpdateWhenNameExists() {
        UUID id = UUID.randomUUID();
        Agence entity = Agence.builder().id(id).build();
        AgenceUpdateRequest request = AgenceUpdateRequest.builder()
                .id(id)
                .nom("Agence A")
                .build();

        when(agenceRepository.findById(id)).thenReturn(Optional.of(entity));
        when(agenceRepository.existsByNomAndIdNot("Agence A", id)).thenReturn(true);

        assertThatThrownBy(() -> agenceService.update(request))
                .isInstanceOf(ConflictException.class)
                .hasMessage("Agence name already exists");
    }

    @Test
    void shouldThrowOnUpdateWhenPointVentesEmpty() {
        UUID id = UUID.randomUUID();
        Agence entity = Agence.builder().id(id).build();
        AgenceUpdateRequest request = AgenceUpdateRequest.builder()
                .id(id)
                .pointVenteIds(List.of())
                .build();

        when(agenceRepository.findById(id)).thenReturn(Optional.of(entity));

        assertThatThrownBy(() -> agenceService.update(request))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Agence must be associated with at least one point de vente");
    }

    @Test
    void shouldUpdateAgence() {
        UUID id = UUID.randomUUID();
        Agence entity = Agence.builder().id(id).build();
        AgenceUpdateRequest request = AgenceUpdateRequest.builder()
                .id(id)
                .nom("Agence B")
                .build();
        AgenceResponse response = AgenceResponse.builder().id(id).build();

        when(agenceRepository.findById(id)).thenReturn(Optional.of(entity));
        when(agenceRepository.existsByNomAndIdNot("Agence B", id)).thenReturn(false);
        doNothing().when(agenceMapper).update(request, entity);
        when(agenceRepository.save(entity)).thenReturn(entity);
        when(agenceMapper.toResponse(entity)).thenReturn(response);

        AgenceResponse result = agenceService.update(request);

        assertThat(result).isEqualTo(response);
        verify(agenceMapper).update(request, entity);
    }
}
