package com.sharif.voucher_api.service;

import com.sharif.voucher_api.dto.request.EventCreateRequest;
import com.sharif.voucher_api.dto.request.EventUpdateRequest;
import com.sharif.voucher_api.dto.response.EventResponse;
import com.sharif.voucher_api.entityandrepo.event.EventEntity;
import com.sharif.voucher_api.entityandrepo.event.EventRepository;
import com.sharif.voucher_api.entityandrepo.user.UserRepository;
import com.sharif.voucher_api.error.NotFoundException;
import com.sharif.voucher_api.mapper.EventMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final EventMapper eventMapper;

    @Transactional
    public EventResponse create(EventCreateRequest request) {
        if (!userRepository.existsById(request.getUserId())) {
            throw new NotFoundException("User not found");
        }
        EventEntity entity = eventMapper.toEntity(request);
        return eventMapper.toResponse(eventRepository.save(entity));
    }

    @Transactional
    public EventResponse update(EventUpdateRequest request) {
        EventEntity entity = eventRepository.findById(request.getId())
                .orElseThrow(() -> new NotFoundException("Event not found"));
        if (request.getUserId() != null && !userRepository.existsById(request.getUserId())) {
            throw new NotFoundException("User not found");
        }
        eventMapper.update(request, entity);
        return eventMapper.toResponse(eventRepository.save(entity));
    }

    @Transactional(readOnly = true)
    public EventResponse getById(UUID id) {
        return eventMapper.toResponse(eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Event not found")));
    }

    @Transactional(readOnly = true)
    public List<EventResponse> getAll() {
        return eventRepository.findAll().stream().map(eventMapper::toResponse).toList();
    }

    @Transactional
    public void delete(UUID id) {
        EventEntity entity = eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Event not found"));
        eventRepository.delete(entity);
    }
}
