package com.snapp.snapppay.club.service.provider;

import com.snapp.snapppay.club.domain.entity.Provider;
import com.snapp.snapppay.club.domain.request.ProviderRegisterRequest;
import com.snapp.snapppay.club.repository.ProviderRepository;
import com.snapp.snapppay.club.service.user.UserLoaderService;
import com.snapp.snapppay.club.service.user.UserRoleService;
import com.snapp.snapppay.club.validation.ProviderRegisterRequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProviderRegisterServiceImpl implements ProviderRegisterService {

    private final ProviderRepository providerRepository;
    private final ProviderRegisterRequestValidator registerRequestValidator;
    private final UserLoaderService userLoaderService;
    private final UserRoleService userRoleService;

    @Override
    @Transactional
    public void register(ProviderRegisterRequest providerRegisterRequest) {
        registerRequestValidator.validate(providerRegisterRequest);
        Provider provider = map(providerRegisterRequest);
        providerRepository.save(provider);
        userRoleService.makeProvider(providerRegisterRequest.getUserId());
    }

    private Provider map(ProviderRegisterRequest providerRegisterRequest) {
        Provider provider = new Provider();
        provider.setTitle(providerRegisterRequest.getTitle());
        provider.setUrl(providerRegisterRequest.getUrl());
        provider.setUser(userLoaderService.load(providerRegisterRequest.getUserId()));
        return provider;
    }
}
