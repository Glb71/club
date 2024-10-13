package com.snapp.snapppay.club.api;

import com.snapp.snapppay.club.domain.request.LoginRequest;
import com.snapp.snapppay.club.domain.request.UserRegisterRequest;
import com.snapp.snapppay.club.service.LoginService;
import com.snapp.snapppay.club.service.UserRegisterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthApi {

    private final LoginService loginService;
    private final UserRegisterService userRegisterService;

    @PostMapping("/login")
    public String login(@Valid @RequestBody LoginRequest loginRequest) {
        return loginService.login(loginRequest);
    }

    @PostMapping("/register")
    public String register(@Valid @RequestBody UserRegisterRequest userRegisterRequest) {
        userRegisterService.register(userRegisterRequest);
        return "success";
    }

}
