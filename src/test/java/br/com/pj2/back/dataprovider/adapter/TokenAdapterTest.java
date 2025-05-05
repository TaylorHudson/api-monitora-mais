package br.com.pj2.back.dataprovider.adapter;

import static org.junit.jupiter.api.Assertions.*;

import br.com.pj2.back.core.exception.UnauthorizedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Base64;

class TokenAdapterTest {

    private TokenAdapter tokenAdapter;

    private final String secret = Base64.getEncoder().encodeToString("my-very-secret-key-which-needs-to-be-long".getBytes());

    @BeforeEach
    void setUp() {
        tokenAdapter = new TokenAdapter();
        ReflectionTestUtils.setField(tokenAdapter, "key", secret);
        long accessTokenExpiry = 1000 * 60 * 10;
        ReflectionTestUtils.setField(tokenAdapter, "accessExpiresIn", accessTokenExpiry);
        long refreshTokenExpiry = 1000 * 60 * 60;
        ReflectionTestUtils.setField(tokenAdapter, "refreshExpiresIn", refreshTokenExpiry);
    }

    @Test
    void shouldGenerateAndValidateAccessToken() {
        String subject = "user123";
        String accessToken = tokenAdapter.generateAccessToken(subject);

        assertNotNull(accessToken);
        assertEquals(subject, tokenAdapter.extractSubject(accessToken));
        assertTrue(tokenAdapter.isAccessToken(accessToken));
        assertTrue(tokenAdapter.isTokenValid(accessToken, subject));
    }

    @Test
    void shouldGenerateAndValidateRefreshToken() {
        String subject = "user123";
        String refreshToken = tokenAdapter.generateRefreshToken(subject);

        assertNotNull(refreshToken);
        assertEquals(subject, tokenAdapter.extractSubject(refreshToken));
        assertFalse(tokenAdapter.isAccessToken(refreshToken));
        assertTrue(tokenAdapter.isTokenValid(refreshToken, subject));
    }

    @Test
    void shouldReturnFalseIfSubjectDoesNotMatch() {
        String token = tokenAdapter.generateAccessToken("user123");

        assertFalse(tokenAdapter.isTokenValid(token, "wrongUser"));
    }

    @Test
    void shouldReturnFalseIfTokenIsExpired() throws InterruptedException {
        ReflectionTestUtils.setField(tokenAdapter, "accessExpiresIn", 1L); // 1 ms

        String token = tokenAdapter.generateAccessToken("user123");
        Thread.sleep(5);

        assertThrows(UnauthorizedException.class, () -> tokenAdapter.isTokenValid(token, "user123"));
    }

    @Test
    void shouldThrowExceptionForInvalidToken() {
        String invalidToken = "invalid.token.value";

        assertThrows(Exception.class, () -> tokenAdapter.extractSubject(invalidToken));
        assertThrows(Exception.class, () -> tokenAdapter.isAccessToken(invalidToken));
    }
}
