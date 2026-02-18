package com.octopus.voucher.service;

import com.octopus.voucher.dto.request.UserCreateRequest;
import com.octopus.voucher.dto.request.UserUpdateRequest;
import com.octopus.voucher.dto.response.UserResponse;
import com.octopus.voucher.entity.User;
import com.octopus.voucher.error.ConflictException;
import com.octopus.voucher.error.NotFoundException;
import com.octopus.voucher.mapper.UserMapper;
import com.octopus.voucher.repository.AgenceRepository;
import com.octopus.voucher.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.octopus.voucher.enumeration.RoleEnum.ADMINISTRATEUR;
import static com.octopus.voucher.enumeration.RoleEnum.AUDITEUR;
import static com.octopus.voucher.enumeration.StatutEnum.ACTIVE;
import static com.octopus.voucher.enumeration.StatutEnum.DESACTIVE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private AgenceRepository agenceRepository;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private UserService userService;

    @Test
    void shouldCreateUser() {
        UserCreateRequest userCreateRequest = UserCreateRequest.builder()
                .matricule("M958")
                .nom("Jeane")
                .prenom("Dupont")
                .dateNaissance(LocalDate.of(1991,5,20))
                .password("password789")
                .agenceId(null)
                .username("dupont.jeane")
                .statutEnum(ACTIVE)
                .email("jeanedupont@Gmail.com")
                .numeroTelephone("70000004")
                .roleEnum(ADMINISTRATEUR)
                .build();
        User user = User.builder()
                .matricule("M958")
                .nom("Jeane")
                .prenom("Dupont")
                .dateNaissance(LocalDate.of(1991,5,20))
                .password("password789")
                .agence(null)
                .username("dupont.jeane")
                .statutEnum(ACTIVE)
                .email("jeanedupont@Gmail.com")
                .numeroTelephone("70000004")
                .roleEnum(ADMINISTRATEUR)
                .events(null)
                .build();

        UserResponse userResponse = UserResponse.builder()
                .nom("Jeane")
                .prenom("Dupont")
                .username("dupont.jeane")
                .email("jeanedupont@gmail.com")
                .roleEnum(ADMINISTRATEUR)
                .statutEnum(ACTIVE)
                .roleEnum(ADMINISTRATEUR)
                .build();
        when(userRepository.existsByMatricule("M958")).thenReturn(false);
        when(userMapper.toEntity(userCreateRequest)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toResponse(user)).thenReturn(userResponse);

        UserResponse result = userService.create(userCreateRequest);

        assertThat(result).isEqualTo(userResponse);
    }

    @Test
    void shouldThrowOnCreateWhenMatriculeExists() {
        UserCreateRequest userCreateRequest = UserCreateRequest.builder()
                .matricule("M958")
                .nom("Jeane")
                .prenom("Dupont")
                .dateNaissance(LocalDate.of(1991, 5, 20))
                .password("password789")
                .agenceId(null)
                .username("dupont.jeane")
                .statutEnum(ACTIVE)
                .email("jeanedupont@Gmail.com")
                .numeroTelephone("70000004")
                .roleEnum(ADMINISTRATEUR)
                .build();

        when(userRepository.existsByMatricule("M958")).thenReturn(true);

        assertThatThrownBy(() -> userService.create(userCreateRequest))
                .isInstanceOf(ConflictException.class)
                .hasMessage("Matricule already exists");
    }

    @Test
    void shouldThrowOnCreateWhenAgenceMissing() {
        UUID agenceId = UUID.randomUUID();
        UserCreateRequest userCreateRequest = UserCreateRequest.builder()
                .matricule("M958")
                .nom("Jeane")
                .prenom("Dupont")
                .dateNaissance(LocalDate.of(1991, 5, 20))
                .password("password789")
                .agenceId(agenceId)
                .username("dupont.jeane")
                .statutEnum(ACTIVE)
                .email("jeanedupont@Gmail.com")
                .numeroTelephone("70000004")
                .roleEnum(ADMINISTRATEUR)
                .build();

        when(userRepository.existsByMatricule("M958")).thenReturn(false);
        when(agenceRepository.existsById(agenceId)).thenReturn(false);

        assertThatThrownBy(() -> userService.create(userCreateRequest))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Agence not found");
    }

    @Test
    void shouldUpdateUser() {
        UUID id = UUID.randomUUID();
        User user = User.builder()
                .id(id)
                .matricule("M123")
                .nom("Doe")
                .prenom("John")
                .password("password123")
                .agence(null)
                .username("john.doe")
                .statutEnum(ACTIVE)
                .email("johndoe@gmail.com")
                .numeroTelephone("70000001")
                .roleEnum(ADMINISTRATEUR)
                .accounts(null)
                .events(null)
                .build();

        UserResponse userResponse = UserResponse.builder()
                .matricule("M958")
                .nom("Jeane")
                .prenom("Dupont")
                .dateNaissance(LocalDate.of(1991,5,20))
                .agenceId(null)
                .username("dupont.jeane")
                .statutEnum(ACTIVE)
                .email("jeanedupont@Gmail.com")
                .numeroTelephone("70000004")
                .roleEnum(ADMINISTRATEUR)
                .build();

        UserUpdateRequest userUpdateRequest = UserUpdateRequest.builder()
                .id(id)
                .matricule("M958")
                .nom("Jeane")
                .prenom("Dupont")
                .dateNaissance(LocalDate.of(1991,5,20))
                .password("password789")
                .agenceId(null)
                .username("dupont.jeane")
                .statutEnum(ACTIVE)
                .email("jeanedupont@Gmail.com")
                .numeroTelephone("70000004")
                .roleEnum(ADMINISTRATEUR)
                .build();

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userRepository.existsByMatriculeAndIdNot("M958", id)).thenReturn(false);
        when(userRepository.save(user)).thenReturn(user);
        doNothing().when(userMapper).update(userUpdateRequest, user);
        when(userMapper.toResponse(user)).thenReturn(userResponse);

        UserResponse result = userService.update(userUpdateRequest);

        assertThat(result).isEqualTo(userResponse);
        verify(userMapper).update(userUpdateRequest, user);
        verify(userRepository).save(user);
    }

    @Test
    void shouldThrowOnUpdateWhenUserMissing() {
        UUID id = UUID.randomUUID();
        UserUpdateRequest userUpdateRequest = UserUpdateRequest.builder()
                .id(id)
                .nom("Jeane")
                .build();

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.update(userUpdateRequest))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("User not found");
    }

    @Test
    void shouldThrowOnUpdateWhenMatriculeExists() {
        UUID id = UUID.randomUUID();
        User user = User.builder().id(id).build();
        UserUpdateRequest userUpdateRequest = UserUpdateRequest.builder()
                .id(id)
                .matricule("M958")
                .build();

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userRepository.existsByMatriculeAndIdNot("M958", id)).thenReturn(true);

        assertThatThrownBy(() -> userService.update(userUpdateRequest))
                .isInstanceOf(ConflictException.class)
                .hasMessage("Matricule already exists");
    }

    @Test
    void shouldReturnUserById() {
        UUID id = UUID.randomUUID();
        User user = User.builder()
                .id(id)
                .matricule("M789")
                .nom("Jean")
                .prenom("Dupont")
                .password("password789")
                .agence(null)
                .username("dupont.jean")
                .statutEnum(ACTIVE)
                .email("jeandupont@Gmail.com")
                .numeroTelephone("70000003")
                .roleEnum(ADMINISTRATEUR)
                .accounts(null)
                .events(null)
                .build();

        UserResponse userResponse = UserResponse.builder()
                .nom("Jean")
                .prenom("Dupont")
                .username("dupont.jean")
                .email("jeandupont@gmail.com")
                .roleEnum(ADMINISTRATEUR)
                .statutEnum(ACTIVE)
                .roleEnum(ADMINISTRATEUR)
                .build();
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userMapper.toResponse(user)).thenReturn(userResponse);

        UserResponse result = userService.getById(id);
        assertThat(result).isEqualTo(userResponse);
    }

    @Test
    void shouldReturnAllUsers() {
        User user1 = User.builder()
                .matricule("M123")
                .nom("Doe")
                .prenom("John")
                .password("password123")
                .agence(null)
                .username("john.doe")
                .statutEnum(ACTIVE)
                .email("johndoe@gmail.com")
                .numeroTelephone("70000001")
                .roleEnum(ADMINISTRATEUR)
                .accounts(null)
                .events(null)
                .build();

        User user2 = User.builder()
                .matricule("M123")
                .nom("smith")
                .prenom("Will")
                .password("password345")
                .agence(null)
                .username("will.smith")
                .statutEnum(ACTIVE)
                .email("willsmith@gmail.com")
                .numeroTelephone("70000002")
                .roleEnum(AUDITEUR)
                .accounts(null)
                .events(null)
                .build();

        UserResponse response1 = UserResponse.builder()
                .nom("Doe")
                .prenom("John")
                .username("john.doe")
                .email("johndoe@gmail.com")
                .roleEnum(ADMINISTRATEUR)
                .statutEnum(ACTIVE)
                .build();

        UserResponse response2 = UserResponse.builder()
                .nom("Smith")
                .prenom("Will")
                .username("will.smith")
                .email("willsmith@gmail.com")
                .roleEnum(AUDITEUR)
                .statutEnum(ACTIVE)
                .build();

        when(userRepository.findAll()).thenReturn(List.of(user1, user2));
        when(userMapper.toResponse(user1)).thenReturn(response1);
        when(userMapper.toResponse(user2)).thenReturn(response2);

        List<UserResponse> result = userService.getAll();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getNom()).isEqualTo("Doe");
        assertThat(result.get(1)).isEqualTo(response2);



    }

    @Test
    void shouldDeleteUser() {
        UUID id = UUID.randomUUID();
        User user = User.builder()
                .id(id)
                .matricule("M123")
                .nom("Doe")
                .prenom("John")
                .password("password123")
                .agence(null)
                .username("john.doe")
                .statutEnum(ACTIVE)
                .email("johndoe@gmail.com")
                .numeroTelephone("70000001")
                .roleEnum(ADMINISTRATEUR)
                .accounts(null)
                .events(null)
                .build();
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        userService.delete(id);
        assertThat(user.getStatutEnum()).isEqualTo(DESACTIVE);
        assertThat(user.getMatricule()).isEqualTo("M123-DELETED-" + id);
        verify(userRepository).save(user);
    }

    @Test
    void shouldThrowOnDeleteWhenUserMissing() {
        UUID id = UUID.randomUUID();
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.delete(id))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("User not found");

        verify(userRepository, never()).save(any());
    }
}
