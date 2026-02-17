package com.octopus.voucher.controller;

import com.octopus.voucher.dto.request.RegionalCreateRequest;
import com.octopus.voucher.dto.request.RegionalUpdateRequest;
import com.octopus.voucher.dto.response.RegionalResponse;
import com.octopus.voucher.service.RegionalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/regionals")
@RequiredArgsConstructor
public class RegionalController {
    private final RegionalService regionalService;

    @PostMapping
    public ResponseEntity<RegionalResponse> create(@Valid @RequestBody RegionalCreateRequest request) {
        return new ResponseEntity<>(regionalService.create(request), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<RegionalResponse> update(@Valid @RequestBody RegionalUpdateRequest request) {
        return ResponseEntity.ok(regionalService.update(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegionalResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(regionalService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<RegionalResponse>> getAll() {

        return ResponseEntity.ok(regionalService.getAll());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        regionalService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
