package com.snapp.snapppay.club.security.jwt;

import com.snapp.snapppay.club.exception.ExceptionMessageCode;
import com.snapp.snapppay.club.security.UserDetailManager;
import com.snapp.snapppay.club.security.UserPrincipal;
import com.snapp.snapppay.club.util.StringUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TokenServiceImpl implements TokenService {

    private final JwtConfig jwtConfig;
    private final JwtParser jwtParser;
    private final UserDetailManager userDetailManager;

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

    @Override
    public UserPrincipal getCurrentUserPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new AuthorizationServiceException(ExceptionMessageCode.INVALID_AUTHORIZATION);
        } else {
            return getUserPrincipal(authentication);
        }
    }

    private UserPrincipal getUserPrincipal(Authentication authentication) {
        if (authentication.getPrincipal() instanceof UserPrincipal) {
            return (UserPrincipal) authentication.getPrincipal();
        } else {
            throw new AuthorizationServiceException(ExceptionMessageCode.INVALID_AUTHORIZATION);
        }
    }

    private void authenticate(String token) {
        Jws<Claims> claimsJws = pars(token);
        authenticate(claimsJws.getBody());
    }

    private void authenticate(Claims claims) {
        UserDetails userDetails = userDetailManager.loadUserByUsername(claims.getSubject());
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));
    }

    private Jws<Claims> pars(String token) {
        return jwtParser
                .parseClaimsJws(token);
    }
}
