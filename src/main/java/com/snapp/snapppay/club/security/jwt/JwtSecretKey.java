package com.snapp.snapppay.club.security.jwt;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
@RequiredArgsConstructor
public class JwtSecretKey {

    private final JwtConfig jwtConfig;

    @Bean
    public SecretKey secretKey() {
        return Keys.hmacShaKeyFor(jwtConfig.getSecretKey().getBytes());
    }

    @Bean
    public JwtParser jwtParser() {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey())
                .build();
    }

}
