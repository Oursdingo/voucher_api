package com.octopus.voucher.controller;

import com.octopus.voucher.dto.request.VoucherCreateRequest;
import com.octopus.voucher.dto.request.VoucherUpdateRequest;
import com.octopus.voucher.dto.response.VoucherResponse;
import com.octopus.voucher.service.VoucherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/vouchers")
@RequiredArgsConstructor
public class VoucherController {
    private final VoucherService voucherService;

    @PostMapping
    public ResponseEntity<VoucherResponse> create(@Valid @RequestBody VoucherCreateRequest request) {
        return new ResponseEntity<>(voucherService.create(request), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<VoucherResponse> update(@Valid @RequestBody VoucherUpdateRequest request) {
        return ResponseEntity.ok(voucherService.update(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VoucherResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(voucherService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<VoucherResponse>> getAll() {

        return ResponseEntity.ok(voucherService.getAll());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        voucherService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
