package com.snapp.snapppay.club.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtTokenVerifier extends OncePerRequestFilter {

    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (tokenService.haveTokenHeader(request)) {
            try {
                tokenService.authenticate(request);
            } catch (ExpiredJwtException e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "token expired");
                return;
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "invalid token format");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
