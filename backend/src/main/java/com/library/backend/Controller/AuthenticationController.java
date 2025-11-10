package com.library.backend.Controller;

import com.library.backend.DTOs.AuthRequestDto;
import com.library.backend.DTOs.AuthResponseDto;
import com.library.backend.Service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/authenticate")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody @NonNull AuthRequestDto loginRequest) {
        var response = authenticationService.Login(loginRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@RequestBody @NonNull AuthRequestDto registerRequest) {
        var response = authenticationService.Register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        var message = authenticationService.Logout(request);
        return ResponseEntity.ok(message);
    }

}
