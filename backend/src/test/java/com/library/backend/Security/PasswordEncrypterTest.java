package com.library.backend.Security;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

class PasswordEncrypterTest {

    @Test
    void passwordEncoder_shouldEncodeAndMatch() {
        PasswordEncrypter underTest = new PasswordEncrypter();
        PasswordEncoder encoder = underTest.passwordEncoder();

        String raw = "mySecret123!";
        String hash = encoder.encode(raw);

        assertThat(hash).isNotEqualTo(raw);
        assertThat(encoder.matches(raw, hash)).isTrue();
        assertThat(encoder.matches("wrong", hash)).isFalse();
    }
}
