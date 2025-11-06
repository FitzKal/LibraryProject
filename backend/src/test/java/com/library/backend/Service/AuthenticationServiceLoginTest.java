package com.library.backend.Service;

import com.library.backend.DTOs.AuthRequestDto;
import com.library.backend.DTOs.AuthResponseDto;
import com.library.backend.Security.PasswordEncrypter;
import com.library.backend.models.Role;
import com.library.backend.models.User;
import com.library.backend.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceLoginTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncrypter passwordEncrypter;
    @Mock
    private JWTService jwtService;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private AuthenticationService underTest;

    @Test
    void login_shouldReturnToken_whenPasswordMatches() {
        // Given
        AuthRequestDto req = AuthRequestDto.builder().username("alice").password("plain").build();
        User u = User.builder().username("alice").password("enc").role(Role.ADMIN).build();
        given(userRepository.findByUsername("alice")).willReturn(u);
        given(passwordEncrypter.passwordEncoder()).willReturn(bCryptPasswordEncoder);
        given(bCryptPasswordEncoder.matches("plain", "enc")).willReturn(true);
        given(jwtService.generateToken("alice")).willReturn("tok");

        // When
        AuthResponseDto resp = underTest.Login(req);

        // Then
        assertThat(resp.getUsername()).isEqualTo("alice");
        assertThat(resp.getRole()).isEqualTo(Role.ADMIN);
        assertThat(resp.getAccessToken()).isEqualTo("tok");
    }

    @Test
    void login_shouldThrow_whenUserMissingOrPasswordMismatch() {
        AuthRequestDto req = AuthRequestDto.builder().username("ghost").password("p").build();
        given(userRepository.findByUsername("ghost")).willReturn(null);
        assertThrows(RuntimeException.class, () -> underTest.Login(req));


        User u = User.builder().username("bob").password("enc").role(Role.USER).build();
        given(userRepository.findByUsername("bob")).willReturn(u);
        given(passwordEncrypter.passwordEncoder()).willReturn(bCryptPasswordEncoder);
        given(bCryptPasswordEncoder.matches("p", "enc")).willReturn(false);
        AuthRequestDto req2 = AuthRequestDto.builder().username("bob").password("p").build();
        assertThrows(RuntimeException.class, () -> underTest.Login(req2));
    }
}
