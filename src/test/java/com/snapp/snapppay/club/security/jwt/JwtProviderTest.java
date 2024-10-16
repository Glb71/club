package com.snapp.snapppay.club.security.jwt;

import com.snapp.snapppay.club.security.jwt.JwtConfig;
import com.snapp.snapppay.club.security.jwt.JwtProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import javax.crypto.SecretKey;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtProviderTest {
    private JwtProvider jwtProvider;
    private SecretKey secretKey;
    @Mock
    private JwtConfig jwtConfig;
    @Mock
    private Authentication authentication;
    private JwtParser jwtParser;
    private static final String TEST_USERNAME = "testUser";

    @BeforeEach
    void setup() {
        when(jwtConfig.getSecretKey()).thenReturn("testJWTSecretKey_2024!#%&()=+?@^_|~");
        when(jwtConfig.getTokenExpirationSecond()).thenReturn(900L);
        secretKey = Keys.hmacShaKeyFor(jwtConfig.getSecretKey().getBytes());
        jwtProvider = new JwtProvider(jwtConfig, secretKey);
        jwtParser = Jwts.parserBuilder().setSigningKey(secretKey).build();
    }

    @Test
    void testGeneratedToken() {
        when(authentication.getName()).thenReturn(TEST_USERNAME);
        String token = jwtProvider.jwtToken(authentication);
        assertNotNull(token,"Generated token can not be null");

        Claims body = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
        assertEquals(TEST_USERNAME, body.getSubject());
    }

    @Test
    void testExpiration() throws InterruptedException {
        when(jwtConfig.getTokenExpirationSecond()).thenReturn(5L);
        String token = jwtProvider.jwtToken(authentication);
        Thread.sleep(6000);
        assertThrows(ExpiredJwtException.class, () -> jwtParser.parseClaimsJws(token));
    }

}
