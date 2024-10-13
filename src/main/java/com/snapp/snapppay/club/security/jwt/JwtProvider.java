package com.snapp.snapppay.club.security.jwt;

import com.snapp.snapppay.club.util.DateUtil;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Collection;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;

    public String jwtToken(Authentication authentication) {
        return jwtToken(authentication.getName(), authentication.getAuthorities());
    }

    private String jwtToken(String subject,
                            Collection<? extends GrantedAuthority> authorities) {
        return Jwts.builder()
                .setSubject(subject)
                .claim("authorities", authorities)
                .setIssuedAt(DateUtil.getCurrentDateTime())
                .setExpiration(DateUtil.addSecondsToCurrentDateTime(jwtConfig.getTokenExpirationSecond()))
                .signWith(secretKey)
                .compact();
    }

}
