package com.library.backend.Converters;

import com.library.backend.DTOs.UserResponseDto;
import com.library.backend.models.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDTOConverter {
    UserResponseDto convertUserToResponse(User user);
}
