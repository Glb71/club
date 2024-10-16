package com.snapp.snapppay.club.config;

import com.snapp.snapppay.club.security.UserPrincipal;
import com.snapp.snapppay.club.security.jwt.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class JpaAuditorAware implements AuditorAware<Long> {

    private final TokenService tokenService;

    @Override
    public Optional<Long> getCurrentAuditor() {
        Optional<Long> userId = Optional.empty();
        try {
            UserPrincipal userPrincipal = tokenService.getCurrentUserPrincipal();
            userId = Optional.of(userPrincipal.getId());
        } catch (AuthorizationServiceException e) {
            log.error("not authorized auditor persisting data");
        }
        return userId;
    }
}
