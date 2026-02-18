package com.octopus.voucher.service;

import com.octopus.voucher.dto.request.EventCreateRequest;
import com.octopus.voucher.dto.request.EventUpdateRequest;
import com.octopus.voucher.dto.response.EventResponse;
import com.octopus.voucher.entity.Event;
import com.octopus.voucher.enumeration.ActionEnum;
import com.octopus.voucher.error.NotFoundException;
import com.octopus.voucher.mapper.EventMapper;
import com.octopus.voucher.repository.EventRepository;
import com.octopus.voucher.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {
    @Mock
    private EventRepository eventRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private EventMapper eventMapper;
    @InjectMocks
    private EventService eventService;

    @Test
    void shouldThrowOnCreateWhenUserMissing() {
        UUID userId = UUID.randomUUID();
        EventCreateRequest request = EventCreateRequest.builder()
                .actionEnum(ActionEnum.CONNEXION)
                .entite("USER")
                .date(LocalDateTime.now())
                .adresse("127.0.0.1")
                .userId(userId)
                .build();

        when(userRepository.existsById(userId)).thenReturn(false);

        assertThatThrownBy(() -> eventService.create(request))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("User not found");
    }

    @Test
    void shouldCreateEvent() {
        UUID userId = UUID.randomUUID();
        EventCreateRequest request = EventCreateRequest.builder()
                .actionEnum(ActionEnum.CONNEXION)
                .entite("USER")
                .date(LocalDateTime.now())
                .adresse("127.0.0.1")
                .userId(userId)
                .build();

        Event entity = Event.builder().id(UUID.randomUUID()).build();
        EventResponse response = EventResponse.builder().id(entity.getId()).build();

        when(userRepository.existsById(userId)).thenReturn(true);
        when(eventMapper.toEntity(request)).thenReturn(entity);
        when(eventRepository.save(entity)).thenReturn(entity);
        when(eventMapper.toResponse(entity)).thenReturn(response);

        EventResponse result = eventService.create(request);

        assertThat(result).isEqualTo(response);
    }

    @Test
    void shouldThrowOnUpdateWhenEventMissing() {
        UUID id = UUID.randomUUID();
        EventUpdateRequest request = EventUpdateRequest.builder().id(id).build();

        when(eventRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> eventService.update(request))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Event not found");
    }

    @Test
    void shouldThrowOnUpdateWhenUserMissing() {
        UUID id = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        Event entity = Event.builder().id(id).build();
        EventUpdateRequest request = EventUpdateRequest.builder()
                .id(id)
                .userId(userId)
                .build();

        when(eventRepository.findById(id)).thenReturn(Optional.of(entity));
        when(userRepository.existsById(userId)).thenReturn(false);

        assertThatThrownBy(() -> eventService.update(request))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("User not found");
    }

    @Test
    void shouldUpdateEvent() {
        UUID id = UUID.randomUUID();
        Event entity = Event.builder().id(id).build();
        EventUpdateRequest request = EventUpdateRequest.builder()
                .id(id)
                .actionEnum(ActionEnum.DECONNEXION)
                .build();
        EventResponse response = EventResponse.builder().id(id).build();

        when(eventRepository.findById(id)).thenReturn(Optional.of(entity));
        doNothing().when(eventMapper).update(request, entity);
        when(eventRepository.save(entity)).thenReturn(entity);
        when(eventMapper.toResponse(entity)).thenReturn(response);

        EventResponse result = eventService.update(request);

        assertThat(result).isEqualTo(response);
        verify(eventMapper).update(request, entity);
    }
}
