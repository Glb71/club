package com.snapp.snapppay.club.security.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "application.jwt")
public class JwtConfig {

    private String secretKey;
    private String tokenPrefix;
    private String authorizationHeader;
    private Long tokenExpirationSecond;

    public String getTokenPrefix() {
        return tokenPrefix != null ? tokenPrefix.trim().concat(" ") : null;
    }
}
