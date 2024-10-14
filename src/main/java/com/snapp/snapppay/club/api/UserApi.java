package com.snapp.snapppay.club.api;

import com.snapp.snapppay.club.domain.request.PageRequest;
import com.snapp.snapppay.club.domain.response.UserResponse;
import com.snapp.snapppay.club.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserApi {

    private final UserService userService;

    @GetMapping("/search")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<UserResponse> search(@RequestParam(value = "search", required = false) String search, @Valid PageRequest pageRequest) {
        return userService.search(search, pageRequest);
    }
}
