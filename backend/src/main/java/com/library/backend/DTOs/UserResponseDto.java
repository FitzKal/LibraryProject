package com.library.backend.DTOs;

import com.library.backend.models.Role;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
    private Long userId;
    private String username;
    private Role role;


}
