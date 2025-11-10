package com.library.backend.Service;

import com.library.backend.Converters.UserDTOConverter;
import com.library.backend.DTOs.UserRequestDTO;
import com.library.backend.DTOs.UserResponseDto;
import com.library.backend.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final UserDTOConverter userDTOConverter;

    public UserResponseDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(userDTOConverter::convertUserToResponse)
                .orElseThrow(() -> new RuntimeException("User cannot Be found"));
    }

    public List<UserResponseDto> getAll() {
        return userRepository.findAll().stream()
                .map(userDTOConverter::convertUserToResponse)
                .toList();
    }

    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new RuntimeException("User cannot be found");
        }
    }

    public UserResponseDto updateUser(Long id, UserRequestDTO userRequestDTO) {
        if (userRepository.existsById(id)) {
            var userToUpdate = userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Cannot be found"));

            userToUpdate.setRole(userRequestDTO.getRole());
            userToUpdate.setUsername(userRequestDTO.getUsername());
            userRepository.save(userToUpdate);

            return userDTOConverter.convertUserToResponse(userToUpdate);
        }
        throw new RuntimeException("User cannot be updated");
    }
}
