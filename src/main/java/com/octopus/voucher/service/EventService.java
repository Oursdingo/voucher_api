package com.octopus.voucher.service;

import com.octopus.voucher.dto.request.EventCreateRequest;
import com.octopus.voucher.dto.request.EventUpdateRequest;
import com.octopus.voucher.dto.response.EventResponse;
import com.octopus.voucher.entity.Event;
import com.octopus.voucher.repository.EventRepository;
import com.octopus.voucher.repository.UserRepository;
import com.octopus.voucher.error.NotFoundException;
import com.octopus.voucher.mapper.EventMapper;
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
        Event entity = eventMapper.toEntity(request);
        return eventMapper.toResponse(eventRepository.save(entity));
    }

    @Transactional
    public EventResponse update(EventUpdateRequest request) {
        Event entity = eventRepository.findById(request.getId())
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
        Event entity = eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Event not found"));
        eventRepository.delete(entity);
    }
}
