package com.library.backend.DTOs;

import com.library.backend.models.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AuthResponseDto {
    private String username;
    private Role role;
    private String accessToken;
}
