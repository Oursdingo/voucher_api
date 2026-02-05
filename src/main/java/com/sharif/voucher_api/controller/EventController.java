package com.sharif.voucher_api.controller;

import com.sharif.voucher_api.dto.request.EventCreateRequest;
import com.sharif.voucher_api.dto.request.EventUpdateRequest;
import com.sharif.voucher_api.dto.response.EventResponse;
import com.sharif.voucher_api.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @PostMapping
    public ResponseEntity<EventResponse> create(@Valid @RequestBody EventCreateRequest request) {
        return new ResponseEntity<>(eventService.create(request), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<EventResponse> update(@Valid @RequestBody EventUpdateRequest request) {
        return ResponseEntity.ok(eventService.update(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(eventService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<EventResponse>> getAll() {
        return ResponseEntity.ok(eventService.getAll());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        eventService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
