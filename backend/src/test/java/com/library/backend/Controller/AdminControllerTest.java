package com.library.backend.Controller;

import com.library.backend.DTOs.UserRequestDTO;
import com.library.backend.DTOs.UserResponseDto;
import com.library.backend.Service.AdminService;
import com.library.backend.models.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    @Mock
    private AdminService adminService;

    @InjectMocks
    private AdminController underTest;

    @Test
    void testGetUserByIdShouldReturnOk() {
        // Given
        long id = 1L;
        var dto = new UserResponseDto(id, "dzsoni", Role.USER);
        given(adminService.getUserById(id)).willReturn(dto);

        // When
        ResponseEntity<UserResponseDto> res = underTest.getUserById(id);

        // Then
        assertThat(res.getBody()).isEqualTo(dto);
        verify(adminService).getUserById(id);
    }

    @Test
    void testGetAllUsersShouldReturnList() {
        // Given
        var list = List.of(new UserResponseDto(1L, "a", Role.USER));
        given(adminService.getAll()).willReturn(list);

        // When
        var res = underTest.getAllUsers();

        // Then
        assertThat(res.getBody()).isEqualTo(list);
        verify(adminService).getAll();
    }

    @Test
    void testUpdateUserShouldDelegate() {
        // Given
        long id = 5L;
        var req = new UserRequestDTO();
        req.setUsername("new");
        req.setRole(Role.ADMIN);
        var dto = new UserResponseDto(id, "new", Role.ADMIN);
        given(adminService.updateUser(id, req)).willReturn(dto);

        // When
        var res = underTest.updateUser(id, req);

        // Then
        assertThat(res.getBody()).isEqualTo(dto);
        verify(adminService).updateUser(id, req);
    }

    @Test
    void testDeleteUserShouldDelegate() {
        // Given
        long id = 7L;

        // When
        underTest.deleteUser(id);

        // Then
        verify(adminService).deleteUser(id);
    }
}
