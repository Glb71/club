package com.snapp.snapppay.club.service.auth;

import com.snapp.snapppay.club.domain.request.LoginRequest;

public interface LoginService {

    String login(LoginRequest loginRequest);
}
