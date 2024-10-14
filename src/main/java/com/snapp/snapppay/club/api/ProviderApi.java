package com.snapp.snapppay.club.api;

import com.snapp.snapppay.club.domain.request.ProviderRegisterRequest;
import com.snapp.snapppay.club.service.provider.ProviderRegisterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/provider")
@RequiredArgsConstructor
public class ProviderApi {

    private final ProviderRegisterService providerRegisterService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addProvider(@Valid @RequestBody ProviderRegisterRequest providerRegisterRequest) {
        providerRegisterService.register(providerRegisterRequest);
        return "success";
    }


}
