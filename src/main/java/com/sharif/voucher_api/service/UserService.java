package com.sharif.voucher_api.service;

import com.sharif.voucher_api.dto.request.UserCreateRequest;
import com.sharif.voucher_api.dto.request.UserUpdateRequest;
import com.sharif.voucher_api.dto.response.UserResponse;
import com.sharif.voucher_api.entityandrepo.user.UserEntity;
import com.sharif.voucher_api.entityandrepo.user.UserRepository;
import com.sharif.voucher_api.entityandrepo.agence.AgenceRepository;
import com.sharif.voucher_api.enumeration.Statut;
import com.sharif.voucher_api.error.ConflictException;
import com.sharif.voucher_api.error.NotFoundException;
import com.sharif.voucher_api.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AgenceRepository agenceRepository;
    private final UserMapper userMapper;

    @Transactional
    public UserResponse create(UserCreateRequest request) {
        if (userRepository.existsByMatricule(request.getMatricule())) {
            throw new ConflictException("Matricule already exists");
        }
        if (request.getAgenceId() != null && !agenceRepository.existsById(request.getAgenceId())) {
            throw new NotFoundException("Agence not found");
        }
        UserEntity entity = userMapper.toEntity(request);
        if (entity.getStatut() == null) {
            entity.setStatut(Statut.ACTIVE);
        }
        return userMapper.toResponse(userRepository.save(entity));
    }

    @Transactional
    public UserResponse update(UserUpdateRequest request) {
        UserEntity entity = userRepository.findById(request.getId())
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (request.getMatricule() != null
                && userRepository.existsByMatriculeAndIdNot(request.getMatricule(), entity.getId())) {
            throw new ConflictException("Matricule already exists");
        }
        if (request.getAgenceId() != null && !agenceRepository.existsById(request.getAgenceId())) {
            throw new NotFoundException("Agence not found");
        }
        userMapper.update(request, entity);
        return userMapper.toResponse(userRepository.save(entity));
    }

    @Transactional(readOnly = true)
    public UserResponse getById(UUID id) {
        return userMapper.toResponse(userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found")));
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getAll() {
        return userRepository.findAll().stream().map(userMapper::toResponse).toList();
    }

    @Transactional
    public void delete(UUID id) {
        UserEntity entity = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
        entity.setStatut(Statut.DESACTIVE);
        if (entity.getMatricule() != null) {
            entity.setMatricule(entity.getMatricule() + "-DELETED-" + entity.getId());
        }
        userRepository.save(entity);
    }
}
