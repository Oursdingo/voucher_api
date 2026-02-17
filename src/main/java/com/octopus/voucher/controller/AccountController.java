package com.octopus.voucher.controller;

import com.octopus.voucher.dto.request.AccountCreateRequest;
import com.octopus.voucher.dto.request.AccountUpdateRequest;
import com.octopus.voucher.dto.response.AccountResponse;
import com.octopus.voucher.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountResponse> create(@Valid @RequestBody AccountCreateRequest request) {
        return new ResponseEntity<>(accountService.create(request), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<AccountResponse> update(@Valid @RequestBody AccountUpdateRequest request) {
        return ResponseEntity.ok(accountService.update(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getById(@PathVariable UUID id) {

        return ResponseEntity.ok(accountService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<AccountResponse>> getAll() {

        return ResponseEntity.ok(accountService.getAll());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable UUID id) {

        accountService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
