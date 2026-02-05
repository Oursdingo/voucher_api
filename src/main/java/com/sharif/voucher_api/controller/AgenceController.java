package com.sharif.voucher_api.controller;

import com.sharif.voucher_api.dto.request.AgenceCreateRequest;
import com.sharif.voucher_api.dto.request.AgenceUpdateRequest;
import com.sharif.voucher_api.dto.response.AgenceResponse;
import com.sharif.voucher_api.service.AgenceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/agences")
@RequiredArgsConstructor
public class AgenceController {
    private final AgenceService agenceService;

    @PostMapping
    public ResponseEntity<AgenceResponse> create(@Valid @RequestBody AgenceCreateRequest request) {
        return new ResponseEntity<>(agenceService.create(request), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<AgenceResponse> update(@Valid @RequestBody AgenceUpdateRequest request) {
        return ResponseEntity.ok(agenceService.update(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgenceResponse> getById(@PathVariable UUID id) {

        return ResponseEntity.ok(agenceService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<AgenceResponse>> getAll() {

        return ResponseEntity.ok(agenceService.getAll());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable UUID id) {

        agenceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
