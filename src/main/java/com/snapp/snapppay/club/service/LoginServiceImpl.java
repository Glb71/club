package com.snapp.snapppay.club.service;

import com.snapp.snapppay.club.domain.request.LoginRequest;
import com.snapp.snapppay.club.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @Override
    public String login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        return jwtProvider.jwtToken(authenticate);
    }
}
