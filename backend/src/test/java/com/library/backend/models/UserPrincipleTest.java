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

        UserPrinciple underTest = new UserPrinciple(user);

        assertThat(underTest.getUsername()).isEqualTo("alice");
        assertThat(underTest.getPassword()).isEqualTo("enc");
        assertThat(underTest.isAccountNonExpired()).isTrue();
        assertThat(underTest.isAccountNonLocked()).isTrue();
        assertThat(underTest.isCredentialsNonExpired()).isTrue();
        assertThat(underTest.isEnabled()).isTrue();

        List<SimpleGrantedAuthority> auth = underTest.getAuthorities().stream()
                .map(a -> (SimpleGrantedAuthority) a)
                .toList();
        assertThat(auth).containsExactly(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }
}
