package com.library.backend.Service;

import com.library.backend.Converters.UserDTOConverter;
import com.library.backend.DTOs.UserRequestDTO;
import com.library.backend.DTOs.UserResponseDto;
import com.library.backend.models.Role;
import com.library.backend.models.User;
import com.library.backend.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserDTOConverter userDTOConverter;
    @InjectMocks
    private AdminService underTest;

    @Test
    void testGetUserByIdShouldReturnDto() {
        // Given
        Long id = 5L;
        var user = new User();
        user.setUserId(id);
        user.setUsername("dzsoni");
        user.setRole(Role.USER);

        var dto = new UserResponseDto(id, "dzsoni", Role.USER);

        given(userRepository.findById(id)).willReturn(Optional.of(user));
        given(userDTOConverter.convertUserToResponse(user)).willReturn(dto);

        // When
        var result = underTest.getUserById(id);

        // Then
        assertThat(result).isEqualTo(dto);
        verify(userRepository).findById(id);
        verify(userDTOConverter).convertUserToResponse(user);
    }

    @Test
    void testGetUserByIdShouldThrowWhenNotFound() {
        // Given
        Long id = 123L;
        given(userRepository.findById(id)).willReturn(Optional.empty());

        // When / Then
        assertThrows(RuntimeException.class, () -> underTest.getUserById(id));
        verify(userRepository).findById(id);
        verifyNoInteractions(userDTOConverter);
    }

    @Test
    void testGetAllUsersShouldReturnListOfDtos() {
        // Given
        var u1 = new User();
        u1.setUserId(1L);
        u1.setUsername("a");
        u1.setRole(Role.USER);

        var u2 = new User();
        u2.setUserId(2L);
        u2.setUsername("b");
        u2.setRole(Role.ADMIN);

        var d1 = new UserResponseDto(1L, "a", Role.USER);
        var d2 = new UserResponseDto(2L, "b", Role.ADMIN);

        given(userRepository.findAll()).willReturn(List.of(u1, u2));
        given(userDTOConverter.convertUserToResponse(u1)).willReturn(d1);
        given(userDTOConverter.convertUserToResponse(u2)).willReturn(d2);

        // When
        var result = underTest.getAll();

        // Then
        assertThat(result).containsExactly(d1, d2);
        verify(userRepository).findAll();
        verify(userDTOConverter).convertUserToResponse(u1);
        verify(userDTOConverter).convertUserToResponse(u2);
    }

    @Test
    void testDeleteUserShouldDeleteById() {
        // Given
        Long id = 10L;
        given(userRepository.existsById(id)).willReturn(true);

        // When
        underTest.deleteUser(id);

        // Then
        verify(userRepository).deleteById(id);
        verifyNoInteractions(userDTOConverter);
    }

    @Test
    void testUpdateUserShouldSaveAndReturnDto() {
        // Given
        Long id = 7L;
        var existing = new User();
        existing.setUserId(id);
        existing.setUsername("old");
        existing.setRole(Role.USER);

        var req = new UserRequestDTO();
        req.setUsername("newname");
        req.setRole(Role.ADMIN);

        var updatedDto = new UserResponseDto(id, "newname", Role.ADMIN);

        given(userRepository.existsById(id)).willReturn(true);
        given(userRepository.findById(id)).willReturn(Optional.of(existing));
        given(userDTOConverter.convertUserToResponse(existing)).willReturn(updatedDto);

        // When
        var result = underTest.updateUser(id, req);

        // Then
        assertThat(result).isEqualTo(updatedDto);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());
        var saved = captor.getValue();
        assertThat(saved.getUserId()).isEqualTo(id);
        assertThat(saved.getUsername()).isEqualTo("newname");
        assertThat(saved.getRole()).isEqualTo(Role.ADMIN);

        verify(userDTOConverter).convertUserToResponse(existing);
    }

    @Test
    void testUpdateUserShouldThrowWhenNotExists() {
        // Given
        Long id = 9L;
        given(userRepository.existsById(id)).willReturn(false);

        // When / Then
        assertThrows(RuntimeException.class, () ->
                underTest.updateUser(id, new UserRequestDTO()));
        verify(userRepository).existsById(id);
        verifyNoMoreInteractions(userRepository);
        verifyNoInteractions(userDTOConverter);
    }
}
