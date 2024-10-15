package com.snapp.snapppay.club.security.jwt;

import com.snapp.snapppay.club.security.UserPrincipal;
import jakarta.servlet.http.HttpServletRequest;

public interface TokenService {

    boolean haveTokenHeader(HttpServletRequest request);
    void authenticate(HttpServletRequest request);

    UserPrincipal getCurrentUserPrincipal();

}
