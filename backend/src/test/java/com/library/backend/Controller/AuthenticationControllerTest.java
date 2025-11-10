package com.library.backend.Controller;

import com.library.backend.DTOs.AuthRequestDto;
import com.library.backend.DTOs.AuthResponseDto;
import com.library.backend.Service.AuthenticationService;
import com.library.backend.models.Role;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private HttpServletRequest httpRequest;

    @InjectMocks
    private AuthenticationController underTest;

    @Test
    void testLoginShouldReturnOkAndResponseBody() {
        // Given
        var req = AuthRequestDto.builder()
                .username("u")
                .password("p")
                .build();

        var resp = AuthResponseDto.builder()
                .username("u")
                .role(Role.USER)
                .accessToken("t")
                .build();

        given(authenticationService.Login(req)).willReturn(resp);

        // When
        var entity = underTest.login(req);

        // Then
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody()).isEqualTo(resp);
        verify(authenticationService).Login(req);
    }

    @Test
    void testRegisterShouldReturnCreatedAndResponseBody() {
        // Given
        var req = AuthRequestDto.builder()
                .username("new_user")
                .password("secret")
                .build();

        var resp = AuthResponseDto.builder()
                .username("new_user")
                .role(Role.USER)
                .accessToken("new_token")
                .build();

        given(authenticationService.Register(req)).willReturn(resp);

        // When
        var entity = underTest.register(req);

        // Then
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(entity.getBody()).isEqualTo(resp);
        verify(authenticationService).Register(req);
    }

    @Test
    void testLogoutShouldReturnOkAndMessage() {
        // Given
        var message = "Logged out successfully";
        given(authenticationService.Logout(httpRequest)).willReturn(message);

        // When
        var entity = underTest.logout(httpRequest);

        // Then
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody()).isEqualTo(message);
        verify(authenticationService).Logout(httpRequest);
    }
}
