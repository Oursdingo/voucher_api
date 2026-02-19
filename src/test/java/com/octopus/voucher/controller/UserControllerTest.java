package com.octopus.voucher.controller;

import com.octopus.voucher.dto.request.UserCreateRequest;
import com.octopus.voucher.dto.request.UserUpdateRequest;
import com.octopus.voucher.dto.response.UserResponse;
import com.octopus.voucher.entity.User;
import com.octopus.voucher.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static com.octopus.voucher.enumeration.RoleEnum.ADMINISTRATEUR;
import static com.octopus.voucher.enumeration.RoleEnum.AUDITEUR;
import static com.octopus.voucher.enumeration.StatutEnum.ACTIVE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Test
    void shouldReturnAllUsers() throws Exception {
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

        when(userService.getAll()).thenReturn(List.of(response1, response2));
        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].username").value("john.doe"))
                .andExpect(jsonPath("$[1].username").value("will.smith"));
    }

    @Test
    void shouldReturnUserById() throws Exception {
        UserResponse user = UserResponse.builder()
                .id(UUID.randomUUID())
                .nom("Doe")
                .prenom("John")
                .username("john.doe")
                .email("johndoe@gmail.com")
                .roleEnum(ADMINISTRATEUR)
                .statutEnum(ACTIVE)
                .build();
        when(userService.getById(user.getId())).thenReturn(user);
        mockMvc.perform(get("/api/v1/users/{id}", user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("john.doe"))
                .andExpect(jsonPath("$.email").value("johndoe@gmail.com"));

    }

    @Test
    void shouldCreateUser() throws Exception {

        UserResponse userResponse = UserResponse.builder()
                .nom("Jeane")
                .prenom("Dupont")
                .username("dupont.jeane")
                .email("jeanedupont@gmail.com")
                .roleEnum(ADMINISTRATEUR)
                .statutEnum(ACTIVE)
                .roleEnum(ADMINISTRATEUR)
                .build();
        when(userService.create(any(UserCreateRequest.class))).thenReturn(userResponse);
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "matricule": "Merci8958",
                                    "nom": "Jeane",
                                    "prenom": "Dupont",
                                    "dateNaissance": "1991-05-20",
                                    "password": "P@ssword789",
                                    "agenceId": null,
                                    "username": "dupont.jeane",
                                    "statutEnum": "ACTIVE",
                                    "email": "jeanedupont@gmail.com",
                                    "numeroTelephone": "70000004",
                                    "roleEnum": "ADMINISTRATEUR"
                                    }"""))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("dupont.jeane"))
                .andExpect(jsonPath("$.email").value("jeanedupont@gmail.com"));
        verify(userService).create(any(UserCreateRequest.class));

    }

    @Test
    void shouldUpdateUser() throws Exception {
        UUID id = UUID.randomUUID();

        UserResponse userResponse = UserResponse.builder()
                .matricule("MDDFo958")
                .nom("Jeane")
                .prenom("Dupont")
                .dateNaissance(LocalDate.of(1991,5,20))
                .agenceId(null)
                .username("dupont.jeane")
                .statutEnum(ACTIVE)
                .email("jeanedupont@gmail.com")
                .numeroTelephone("70000004")
                .roleEnum(ADMINISTRATEUR)
                .build();
        when(userService.update(any(UserUpdateRequest.class))).thenReturn(userResponse);
        mockMvc.perform(put("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "id": "%s",
                                    "matricule": "MDDFo958",
                                    "nom": "Jeane",
                                    "prenom": "Dupont",
                                    "dateNaissance": "1991-05-20",
                                    "password": "P@ssword789",
                                    "agenceId": null,
                                    "username": "dupont.jeane",
                                    "statutEnum": "ACTIVE",
                                    "email": "jeanedupont@gmail.com",
                                    "numeroTelephone": "70000004",
                                    "roleEnum": "ADMINISTRATEUR"
                                    }""".formatted(id)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("dupont.jeane"))
                .andExpect(jsonPath("$.email").value("jeanedupont@gmail.com"));
        verify(userService).update(any(UserUpdateRequest.class));
    }
    @Test
    void shouldDeleteUser() throws Exception {
        User user = User.builder()
                .id(UUID.randomUUID())
                .nom("Doe")
                .prenom("John")
                .username("john.doe")
                .build();
        doNothing().when(userService).delete(user.getId());
        mockMvc.perform(delete("/api/v1/users/{id}", user.getId()))
                .andExpect(status().isNoContent());
        verify(userService).delete(user.getId());

    }

    @Test
    void shouldRejectCreateWhenRequiredFieldsMissing() throws Exception {
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(userService);
    }

    @Test
    void shouldRejectCreateWhenPasswordInvalid() throws Exception {
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "matricule": "Merci8958",
                                    "nom": "Jeane",
                                    "prenom": "Dupont",
                                    "dateNaissance": "1991-05-20",
                                    "password": "password",
                                    "agenceId": null,
                                    "username": "dupont.jeane",
                                    "statutEnum": "ACTIVE",
                                    "email": "jeanedupont@gmail.com",
                                    "numeroTelephone": "70000004",
                                    "roleEnum": "ADMINISTRATEUR"
                                    }"""))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(userService);
    }

    @Test
    void shouldRejectCreateWhenAgeUnder21() throws Exception {
        String underAgeDate = LocalDate.now().minusYears(20).toString();
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "matricule": "Merci8958",
                                    "nom": "Jeane",
                                    "prenom": "Dupont",
                                    "dateNaissance": "%s",
                                    "password": "P@ssword789",
                                    "agenceId": null,
                                    "username": "dupont.jeane",
                                    "statutEnum": "ACTIVE",
                                    "email": "jeanedupont@gmail.com",
                                    "numeroTelephone": "70000004",
                                    "roleEnum": "ADMINISTRATEUR"
                                    }""".formatted(underAgeDate)))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(userService);
    }

    @Test
    void shouldRejectUpdateWhenOnlyIdProvided() throws Exception {
        UUID id = UUID.randomUUID();
        mockMvc.perform(put("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "id": "%s"
                                    }""".formatted(id)))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(userService);
    }

    @Test
    void shouldRejectUpdateWhenIdMissing() throws Exception {
        mockMvc.perform(put("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "email": "newmail@gmail.com"
                                    }"""))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(userService);
    }

}
