package com.sharif.voucher_api.controller;

import com.sharif.voucher_api.dto.request.AuditCreateRequest;
import com.sharif.voucher_api.dto.request.AuditUpdateRequest;
import com.sharif.voucher_api.dto.response.AuditResponse;
import com.sharif.voucher_api.service.AuditService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/audits")
@RequiredArgsConstructor
public class AuditController {
    private final AuditService auditService;

    @PostMapping
    public ResponseEntity<AuditResponse> create(@Valid @RequestBody AuditCreateRequest request) {
        return new ResponseEntity<>(auditService.create(request), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<AuditResponse> update(@Valid @RequestBody AuditUpdateRequest request) {

        return ResponseEntity.ok(auditService.update(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuditResponse> getById(@PathVariable UUID id) {

        return ResponseEntity.ok(auditService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<AuditResponse>> getAll() {

        return ResponseEntity.ok(auditService.getAll());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable UUID id) {

        auditService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
