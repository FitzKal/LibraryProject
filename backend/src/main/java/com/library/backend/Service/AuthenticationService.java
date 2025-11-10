package com.library.backend.Service;

import com.library.backend.DTOs.AuthRequestDto;
import com.library.backend.DTOs.AuthResponseDto;
import com.library.backend.Security.PasswordEncrypter;
import com.library.backend.models.Role;
import com.library.backend.models.User;
import com.library.backend.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthenticationService {
    private JWTService jwtService;
    private UserRepository userRepository;
    private final PasswordEncrypter passwordEncrypter;

    public AuthResponseDto Login(AuthRequestDto loginRequest) {
        var existingUser = userRepository.findByUsername(loginRequest.getUsername());
        if (existingUser != null
                && passwordEncrypter.passwordEncoder().matches(
                loginRequest.getPassword(), existingUser.getPassword())) {

            var token = jwtService.generateToken(loginRequest.getUsername());
            return AuthResponseDto.builder()
                    .username(loginRequest.getUsername())
                    .role(existingUser.getRole())
                    .accessToken(token)
                    .build();
        }
        throw new RuntimeException("User not found");
    }

    public AuthResponseDto Register(AuthRequestDto registerRequest) {
        if (!userRepository.existsByUsername(registerRequest.getUsername())) {
            User newUser = User.builder()
                    .username(registerRequest.getUsername())
                    .password(passwordEncrypter.passwordEncoder()
                            .encode(registerRequest.getPassword()))
                    .build();
            newUser.setRole(Role.USER);
            userRepository.save(newUser);
            var token = jwtService.generateToken(registerRequest.getUsername());
            return AuthResponseDto.builder()
                    .username(newUser.getUsername())
                    .role(newUser.getRole())
                    .accessToken(token)
                    .build();
        }
        throw new RuntimeException("Login failed");
    }

    public String Logout(HttpServletRequest request) {
        jwtService.addToBlackList(request);
        return "Successfully logged out!";
    }
}
