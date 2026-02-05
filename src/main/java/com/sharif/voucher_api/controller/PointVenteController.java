package com.sharif.voucher_api.controller;

import com.sharif.voucher_api.dto.request.PointVenteCreateRequest;
import com.sharif.voucher_api.dto.request.PointVenteUpdateRequest;
import com.sharif.voucher_api.dto.response.PointVenteResponse;
import com.sharif.voucher_api.service.PointVenteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/points-vente")
@RequiredArgsConstructor
public class PointVenteController {
    private final PointVenteService pointVenteService;

    @PostMapping
    public ResponseEntity<PointVenteResponse> create(@Valid @RequestBody PointVenteCreateRequest request) {
        return new ResponseEntity<>(pointVenteService.create(request), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<PointVenteResponse> update(@Valid @RequestBody PointVenteUpdateRequest request) {
        return ResponseEntity.ok(pointVenteService.update(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PointVenteResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(pointVenteService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<PointVenteResponse>> getAll() {

        return ResponseEntity.ok(pointVenteService.getAll());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        pointVenteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
