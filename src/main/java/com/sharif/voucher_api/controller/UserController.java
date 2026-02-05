package com.sharif.voucher_api.controller;

import com.sharif.voucher_api.dto.request.UserCreateRequest;
import com.sharif.voucher_api.dto.request.UserUpdateRequest;
import com.sharif.voucher_api.dto.response.UserResponse;
import com.sharif.voucher_api.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> create(@Valid @RequestBody UserCreateRequest request) {
        return new ResponseEntity<>(userService.create(request), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<UserResponse> update(@Valid @RequestBody UserUpdateRequest request) {
        return ResponseEntity.ok(userService.update(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
