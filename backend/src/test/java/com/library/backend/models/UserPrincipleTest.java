package com.library.backend.models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserPrincipleTest {

    @AfterEach
    void cleanup() {

    }

    @Test
    void shouldExposeUsernamePasswordAuthoritiesAndFlags() {
        User user = User.builder()
                .userId(1L)
                .username("alice")
                .password("enc")
                .role(Role.ADMIN)
                .build();

        UserPrinciple principal = new UserPrinciple(user);

        assertThat(principal.getUsername()).isEqualTo("alice");
        assertThat(principal.getPassword()).isEqualTo("enc");
        assertThat(principal.isAccountNonExpired()).isTrue();
        assertThat(principal.isAccountNonLocked()).isTrue();
        assertThat(principal.isCredentialsNonExpired()).isTrue();
        assertThat(principal.isEnabled()).isTrue();

        List<SimpleGrantedAuthority> auth = principal.getAuthorities().stream()
                .map(a -> (SimpleGrantedAuthority) a)
                .toList();
        assertThat(auth).containsExactly(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }
}
