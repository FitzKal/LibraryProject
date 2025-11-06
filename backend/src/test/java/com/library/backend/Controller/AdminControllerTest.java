package com.library.backend.Controller;

import com.library.backend.DTOs.UserRequestDTO;
import com.library.backend.DTOs.UserResponseDto;
import com.library.backend.Service.AdminService;
import com.library.backend.models.Role;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.mock;

class AdminControllerTest {

    @Test
    void testGetUserByIdShouldReturnOk() {
        // Given
        var svc = mock(AdminService.class);
        var controller = new AdminController(svc);
        var dto = new UserResponseDto(1L, "dzsoni", Role.USER);
        given(svc.getUserById(1L)).willReturn(dto);

        // When
        ResponseEntity<UserResponseDto> res = controller.getUserById(1L);

        // Then
        assertThat(res.getBody()).isEqualTo(dto);
        verify(svc).getUserById(1L);
    }

    @Test
    void testGetAllUsersShouldReturnList() {
        // Given
        var svc = mock(AdminService.class);
        var controller = new AdminController(svc);
        var list = List.of(new UserResponseDto(1L, "a", Role.USER));
        given(svc.getAll()).willReturn(list);

        // When
        var res = controller.getAllUsers();

        // Then
        assertThat(res.getBody()).isEqualTo(list);
        verify(svc).getAll();
    }

    @Test
    void testUpdateUserShouldDelegate() {
        // Given
        var svc = mock(AdminService.class);
        var controller = new AdminController(svc);
        var req = new UserRequestDTO();
        req.setUsername("new");
        req.setRole(Role.ADMIN);
        var dto = new UserResponseDto(5L, "new", Role.ADMIN);
        given(svc.updateUser(5L, req)).willReturn(dto);

        // When
        var res = controller.updateUser(5L, req);

        // Then
        assertThat(res.getBody()).isEqualTo(dto);
        verify(svc).updateUser(5L, req);
    }
}
