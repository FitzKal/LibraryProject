package com.library.backend.Service;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class JWTServiceTest {

    @Mock
    private HttpServletRequest req;

    @Test
    void extractTokenFromRequest_shouldReturnToken_whenBearerHeaderPresent() {
        // Given
        given(req.getHeader("Authorization")).willReturn("Bearer abc.def.ghi");
        var underTest = new JWTService();

        // When
        var token = underTest.extractTokenFromRequest(req);

        // Then
        assertThat(token).isEqualTo("abc.def.ghi");
    }

    @Test
    void extractTokenFromRequest_shouldThrow_whenHeaderMissing() {
        given(req.getHeader("Authorization")).willReturn(null);
        var underTest = new JWTService();

        assertThrows(RuntimeException.class, () -> underTest.extractTokenFromRequest(req));
    }

    @Test
    void extractTokenFromRequest_shouldThrow_whenHeaderNotBearer() {
        given(req.getHeader("Authorization")).willReturn("Basic something");
        var underTest = new JWTService();

        assertThrows(RuntimeException.class, () -> underTest.extractTokenFromRequest(req));
    }

    @Test
    void extractTokenFromRequest_shouldAllowEmptyTokenAfterPrefix() {
        given(req.getHeader("Authorization")).willReturn("Bearer ");
        var underTest = new JWTService();

        var token = underTest.extractTokenFromRequest(req);
        assertThat(token).isEmpty();
    }

    @Test
    void addToBlackList_shouldAddExtractedToken() {
        given(req.getHeader("Authorization")).willReturn("Bearer token-123");
        var underTest = new JWTService();

        underTest.addToBlackList(req);
        assertThat(underTest.getBlackList()).contains("token-123");
    }

    @Test
    void generateToken_and_getUsernameFromToken_shouldRoundTrip() {
        var underTest = new JWTService();
        var token = underTest.generateToken("alice");
        var username = underTest.getUsernameFromToken(token);
        assertThat(username).isEqualTo("alice");
    }
}
