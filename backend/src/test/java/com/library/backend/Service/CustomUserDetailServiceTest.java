package com.library.backend.Service;

import com.library.backend.models.Role;
import com.library.backend.models.User;
import com.library.backend.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailService underTest;

    @Test
    void loadUserByUsername_shouldReturnUserDetails() {
        User u = User.builder()
                .userId(3L)
                .username("bob")
                .password("enc")
                .role(Role.USER)
                .build();
        given(userRepository.findByUsername("bob")).willReturn(u);

        UserDetails details = underTest.loadUserByUsername("bob");

        assertThat(details.getUsername()).isEqualTo("bob");
        assertThat(details.getPassword()).isEqualTo("enc");
        assertThat(details.getAuthorities()).isNotEmpty();
    }
}
