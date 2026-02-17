package com.octopus.voucher.service;

import com.octopus.voucher.dto.response.UserResponse;
import com.octopus.voucher.entity.User;
import com.octopus.voucher.mapper.UserMapper;
import com.octopus.voucher.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.octopus.voucher.enumeration.RoleEnum.ADMINISTRATEUR;
import static com.octopus.voucher.enumeration.RoleEnum.AUDITEUR;
import static com.octopus.voucher.enumeration.StatutEnum.ACTIVE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private UserService userService;

    @Test
    void shouldCreateUser() {
    }

    @Test
    void shouldUpdateUser() {
    }

    @Test
    void shouldReturnUserById() {
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

        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        List<UserResponse> users = userService.getAll();

        assertThat(users).hasSize(2).containsExactly(users.getFirst(), users.get(1));



    }

    @Test
    void shouldDeleteUser() {
    }
}