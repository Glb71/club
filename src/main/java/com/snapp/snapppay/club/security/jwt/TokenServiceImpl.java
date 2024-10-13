package com.snapp.snapppay.club.security.jwt;

import com.snapp.snapppay.club.util.StringUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final JwtConfig jwtConfig;
    private final JwtParser jwtParser;

    @Override
    public boolean haveTokenHeader(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(jwtConfig.getAuthorizationHeader());
        return !StringUtil.isNullOrEmpty(authorizationHeader)
                && authorizationHeader.startsWith(jwtConfig.getTokenPrefix());
    }

    @Override
    public void authenticate(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(jwtConfig.getAuthorizationHeader());
        String token = authorizationHeader.replace(jwtConfig.getTokenPrefix(), "");
        authenticate(token);
    }


    private void authenticate(String token) {
        Jws<Claims> claimsJws = pars(token);
        authenticate(claimsJws.getBody());
    }

    private void authenticate(Claims claims) {
        authenticateByUsername(claims.getSubject());
    }

    private void authenticate(String username, String password) {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username, password));
    }

    private void authenticateByUsername(String username) {
        authenticate(username, null);
    }

    private Jws<Claims> pars(String token) {
        return jwtParser
                .parseClaimsJws(token);
    }
}
