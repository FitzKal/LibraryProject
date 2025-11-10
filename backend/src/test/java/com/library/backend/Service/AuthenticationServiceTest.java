package com.library.backend.Service;

import com.library.backend.DTOs.AuthRequestDto;
import com.library.backend.DTOs.AuthResponseDto;
import com.library.backend.Security.PasswordEncrypter;
import com.library.backend.models.Role;
import com.library.backend.models.User;
import com.library.backend.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.willAnswer;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncrypter passwordEncrypter;
    @Mock
    private JWTService jwtService;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private HttpServletRequest httpReq;

    @InjectMocks
    private AuthenticationService underTest;

    @Test
    public void testRegisterShouldEncodeAndSaveAndReturnToken() {
        // Given
        var req = new AuthRequestDto();
        req.setUsername("dzsoni");
        req.setPassword("plain");

        var encoded = "encoded-pass";
        var token = "jwt-token";

        given(passwordEncrypter.passwordEncoder()).willReturn(bCryptPasswordEncoder);
        given(bCryptPasswordEncoder.encode("plain")).willReturn(encoded);
        given(jwtService.generateToken("dzsoni")).willReturn(token);
        willAnswer(inv -> inv.getArgument(0)).given(userRepository).save(any(User.class));

        // When
        AuthResponseDto response = underTest.Register(req);

        // Then
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());
        User saved = captor.getValue();
        assertThat(saved.getUsername()).isEqualTo("dzsoni");
        assertThat(saved.getPassword()).isEqualTo(encoded);
        assertThat(saved.getRole()).isEqualTo(Role.USER);

        assertThat(response.getUsername()).isEqualTo("dzsoni");
        assertThat(response.getRole()).isEqualTo(Role.USER);
        assertThat(response.getAccessToken()).isEqualTo(token);

        verify(jwtService).generateToken("dzsoni");
        verify(passwordEncrypter).passwordEncoder();
        verify(bCryptPasswordEncoder).encode("plain");
    }

    @Test
    public void testRegisterShouldPropagateSaveFailure() {
        // Given
        var req = new AuthRequestDto();
        req.setUsername("u");
        req.setPassword("p");

        given(passwordEncrypter.passwordEncoder()).willReturn(bCryptPasswordEncoder);
        given(bCryptPasswordEncoder.encode("p")).willReturn("enc");
        willThrow(new RuntimeException("save failed")).given(userRepository).save(any(User.class));

        // When / Then
        assertThrows(RuntimeException.class, () -> underTest.Register(req));
    }

    @Test
    public void testLogoutShouldCallJwtBlacklistAndReturnMessage() {
        // When
        String msg = underTest.Logout(httpReq);

        // Then
        verify(jwtService).addToBlackList(httpReq);
        assertThat(msg).isEqualTo("Successfully logged out!");
    }
}
