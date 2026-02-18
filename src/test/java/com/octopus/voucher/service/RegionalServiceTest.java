package com.octopus.voucher.service;

import com.octopus.voucher.dto.request.RegionalCreateRequest;
import com.octopus.voucher.dto.request.RegionalUpdateRequest;
import com.octopus.voucher.dto.response.RegionalResponse;
import com.octopus.voucher.entity.Regional;
import com.octopus.voucher.enumeration.DregionalEnum;
import com.octopus.voucher.error.NotFoundException;
import com.octopus.voucher.mapper.RegionalMapper;
import com.octopus.voucher.repository.RegionalRepository;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegionalServiceTest {
    @Mock
    private RegionalRepository regionalRepository;
    @Mock
    private RegionalMapper regionalMapper;
    @InjectMocks
    private RegionalService regionalService;

    @Test
    void shouldCreateRegional() {
        RegionalCreateRequest request = RegionalCreateRequest.builder()
                .nom("Centre")
                .dregionalEnum(DregionalEnum.CENTRE)
                .build();

        Regional entity = Regional.builder().id(UUID.randomUUID()).build();
        RegionalResponse response = RegionalResponse.builder().id(entity.getId()).build();

        when(regionalMapper.toEntity(request)).thenReturn(entity);
        when(regionalRepository.save(entity)).thenReturn(entity);
        when(regionalMapper.toResponse(entity)).thenReturn(response);

        RegionalResponse result = regionalService.create(request);

        assertThat(result).isEqualTo(response);
    }

    @Test
    void shouldThrowOnUpdateWhenRegionalMissing() {
        UUID id = UUID.randomUUID();
        RegionalUpdateRequest request = RegionalUpdateRequest.builder().id(id).build();

        when(regionalRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> regionalService.update(request))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Regional not found");
    }

    @Test
    void shouldUpdateRegional() {
        UUID id = UUID.randomUUID();
        Regional entity = Regional.builder().id(id).build();
        RegionalUpdateRequest request = RegionalUpdateRequest.builder()
                .id(id)
                .nom("Ouest")
                .build();
        RegionalResponse response = RegionalResponse.builder().id(id).build();

        when(regionalRepository.findById(id)).thenReturn(Optional.of(entity));
        doNothing().when(regionalMapper).update(request, entity);
        when(regionalRepository.save(entity)).thenReturn(entity);
        when(regionalMapper.toResponse(entity)).thenReturn(response);

        RegionalResponse result = regionalService.update(request);

        assertThat(result).isEqualTo(response);
        verify(regionalMapper).update(request, entity);
    }

    @Test
    void shouldReturnAllRegionals() {
        Regional entity1 = Regional.builder().id(UUID.randomUUID()).build();
        Regional entity2 = Regional.builder().id(UUID.randomUUID()).build();
        RegionalResponse response1 = RegionalResponse.builder().id(entity1.getId()).build();
        RegionalResponse response2 = RegionalResponse.builder().id(entity2.getId()).build();

        when(regionalRepository.findAll()).thenReturn(List.of(entity1, entity2));
        when(regionalMapper.toResponse(entity1)).thenReturn(response1);
        when(regionalMapper.toResponse(entity2)).thenReturn(response2);

        List<RegionalResponse> result = regionalService.getAll();

        assertThat(result).containsExactly(response1, response2);
    }

    @Test
    void shouldDeleteRegional() {
        UUID id = UUID.randomUUID();
        Regional entity = Regional.builder().id(id).build();

        when(regionalRepository.findById(id)).thenReturn(Optional.of(entity));

        regionalService.delete(id);

        verify(regionalRepository).delete(entity);
    }
}
