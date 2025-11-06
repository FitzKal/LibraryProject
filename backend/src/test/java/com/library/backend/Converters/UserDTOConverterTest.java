package com.library.backend.Converters;

import com.library.backend.DTOs.UserResponseDto;
import com.library.backend.models.Role;
import com.library.backend.models.User;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class UserDTOConverterTest {

    private final UserDTOConverter underTest = Mappers.getMapper(UserDTOConverter.class);

    @Test
    void convertUserToResponse_shouldMapAllFields() {
        User user = User.builder()
                .userId(9L)
                .username("alice")
                .password("secret")
                .role(Role.USER)
                .build();

        UserResponseDto dto = underTest.convertUserToResponse(user);

        assertThat(dto.getUserId()).isEqualTo(9L);
        assertThat(dto.getUsername()).isEqualTo("alice");
        assertThat(dto.getRole()).isEqualTo(Role.USER);
    }
}
