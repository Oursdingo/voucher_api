package com.octopus.voucher.mapper;

import com.octopus.voucher.dto.request.UserCreateRequest;
import com.octopus.voucher.dto.request.UserUpdateRequest;
import com.octopus.voucher.dto.response.UserResponse;
import com.octopus.voucher.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static com.octopus.voucher.enumeration.RoleEnum.ADMINISTRATEUR;
import static com.octopus.voucher.enumeration.StatutEnum.ACTIVE;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    void shouldMapUserToUserResponse() {
        User user = User.builder()
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

        UserResponse response = userMapper.toResponse(user);

        assertThat(response.getNom()).isEqualTo("Doe");
        assertThat(response.getPrenom()).isEqualTo("John");
    }
    @Test
    void shouldUpdateUserFromUserUpdateRequest() {
        User user = User.builder()
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

        UserUpdateRequest userUpdateRequest = UserUpdateRequest.builder()
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
        userMapper.update(userUpdateRequest, user);

        assertThat(user.getNom()).isEqualTo("Jeane");
        assertThat(user.getPrenom()).isEqualTo("Dupont");
    }
}