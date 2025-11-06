package com.library.backend.Security;

import com.library.backend.Service.CustomUserDetailService;
import com.library.backend.Service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class JWTFilterTest {

    @Mock
    private JWTService jwtService;

    @Mock
    private ApplicationContext ctx;

    @Mock
    private CustomUserDetailService uds;

    @Mock
    private HttpServletRequest req;

    @Mock
    private HttpServletResponse res;

    @Mock
    private FilterChain chain;

    @InjectMocks
    private JWTFilter underTest;

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void doFilterInternal_shouldSetAuthentication_whenValidBearerToken() throws Exception {
        // Given
        given(req.getHeader("Authorization")).willReturn("Bearer tok");
        given(jwtService.extractTokenFromRequest(req)).willReturn("tok");
        given(jwtService.getUsernameFromToken("tok")).willReturn("alice");
        given(jwtService.getBlackList()).willReturn(new ArrayList<>());
        UserDetails user = User.withUsername("alice").password("p").roles("USER").build();
        given(ctx.getBean(CustomUserDetailService.class)).willReturn(uds);
        given(uds.loadUserByUsername("alice")).willReturn(user);
        given(jwtService.validateToken("tok", user)).willReturn(true);

        // When
        underTest.doFilterInternal(req, res, chain);

        // Then
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNotNull();
        assertThat(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).isEqualTo(user);
        verify(chain).doFilter(req, res);
    }

    @Test
    void doFilterInternal_shouldSkip_whenNoAuthorizationHeader() throws Exception {
        // Given
        given(req.getHeader("Authorization")).willReturn(null);

        // When
        underTest.doFilterInternal(req, res, chain);

        // Then
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
        verify(chain).doFilter(req, res);
    }

    @Test
    void doFilterInternal_shouldNotAuthenticate_whenTokenBlacklisted() throws Exception {
        // Given
        given(req.getHeader("Authorization")).willReturn("Bearer tok");
        given(jwtService.extractTokenFromRequest(req)).willReturn("tok");
        given(jwtService.getUsernameFromToken("tok")).willReturn("alice");
        given(jwtService.getBlackList()).willReturn(new ArrayList<>(List.of("tok")));

        // When
        underTest.doFilterInternal(req, res, chain);

        // Then
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
        verify(chain).doFilter(req, res);
    }
}
