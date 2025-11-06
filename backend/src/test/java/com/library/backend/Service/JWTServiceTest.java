package com.library.backend.Service;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class JWTServiceTest {

    @Test
    void extractTokenFromRequest_shouldReturnToken_whenBearerHeaderPresent() {
        // Given
        var req = mock(HttpServletRequest.class);
        given(req.getHeader("Authorization")).willReturn("Bearer abc.def.ghi");
        var service = new JWTService();

        // When
        var token = service.extractTokenFromRequest(req);

        // Then
        assertThat(token).isEqualTo("abc.def.ghi");
    }

    @Test
    void extractTokenFromRequest_shouldThrow_whenHeaderMissing() {
        var req = mock(HttpServletRequest.class);
        given(req.getHeader("Authorization")).willReturn(null);
        var service = new JWTService();

        assertThrows(RuntimeException.class, () -> service.extractTokenFromRequest(req));
    }

    @Test
    void extractTokenFromRequest_shouldThrow_whenHeaderNotBearer() {
        var req = mock(HttpServletRequest.class);
        given(req.getHeader("Authorization")).willReturn("Basic something");
        var service = new JWTService();

        assertThrows(RuntimeException.class, () -> service.extractTokenFromRequest(req));
    }

    @Test
    void extractTokenFromRequest_shouldAllowEmptyTokenAfterPrefix() {
        var req = mock(HttpServletRequest.class);
        given(req.getHeader("Authorization")).willReturn("Bearer ");
        var service = new JWTService();

        var token = service.extractTokenFromRequest(req);
        assertThat(token).isEmpty();
    }

    @Test
    void addToBlackList_shouldAddExtractedToken() {
        var req = mock(HttpServletRequest.class);
        given(req.getHeader("Authorization")).willReturn("Bearer token-123");
        var service = new JWTService();

        service.addToBlackList(req);
        assertThat(service.getBlackList()).contains("token-123");
    }

    @Test
    void generateToken_and_getUsernameFromToken_shouldRoundTrip() {
        var service = new JWTService();
        var token = service.generateToken("alice");
        var username = service.getUsernameFromToken(token);
        assertThat(username).isEqualTo("alice");
    }
}
