package com.library.backend.Security;

import com.library.backend.Service.CustomUserDetailService;
import com.library.backend.Service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class JWTFilterTest {

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void doFilterInternal_shouldSetAuthentication_whenValidBearerToken() throws Exception {
        // Mocks
        JWTService jwtService = mock(JWTService.class);
        ApplicationContext ctx = mock(ApplicationContext.class);
        CustomUserDetailService uds = mock(CustomUserDetailService.class);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse res = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        // Given
        given(req.getHeader("Authorization")).willReturn("Bearer tok");
        given(jwtService.extractTokenFromRequest(req)).willReturn("tok");
        given(jwtService.getUsernameFromToken("tok")).willReturn("alice");
        given(jwtService.getBlackList()).willReturn(new ArrayList<>());
        UserDetails user = User.withUsername("alice").password("p").roles("USER").build();
        given(ctx.getBean(CustomUserDetailService.class)).willReturn(uds);
        given(uds.loadUserByUsername("alice")).willReturn(user);
        given(jwtService.validateToken("tok", user)).willReturn(true);

        JWTFilter filter = new JWTFilter(jwtService, ctx);

        // When
        filter.doFilterInternal(req, res, chain);

        // Then
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNotNull();
        assertThat(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).isEqualTo(user);
        verify(chain).doFilter(req, res);
    }

    @Test
    void doFilterInternal_shouldSkip_whenNoAuthorizationHeader() throws Exception {
        JWTService jwtService = mock(JWTService.class);
        ApplicationContext ctx = mock(ApplicationContext.class);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse res = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        given(req.getHeader("Authorization")).willReturn(null);

        JWTFilter filter = new JWTFilter(jwtService, ctx);
        filter.doFilterInternal(req, res, chain);

        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
        verify(chain).doFilter(req, res);
    }

    @Test
    void doFilterInternal_shouldNotAuthenticate_whenTokenBlacklisted() throws Exception {
        JWTService jwtService = mock(JWTService.class);
        ApplicationContext ctx = mock(ApplicationContext.class);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse res = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        given(req.getHeader("Authorization")).willReturn("Bearer tok");
        given(jwtService.extractTokenFromRequest(req)).willReturn("tok");
        given(jwtService.getUsernameFromToken("tok")).willReturn("alice");
        given(jwtService.getBlackList()).willReturn(new ArrayList<>(List.of("tok")));

        JWTFilter filter = new JWTFilter(jwtService, ctx);
        filter.doFilterInternal(req, res, chain);

        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
        verify(chain).doFilter(req, res);
    }
}
