package com.snapp.snapppay.club.service.provider;

import com.snapp.snapppay.club.domain.entity.Provider;
import com.snapp.snapppay.club.domain.entity.User;
import com.snapp.snapppay.club.exception.ExceptionMessageCode;
import com.snapp.snapppay.club.repository.ProviderRepository;
import com.snapp.snapppay.club.service.user.UserLoader;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProviderLoaderImpl implements ProviderLoader {

    private final ProviderRepository providerRepository;
    private final UserLoader userLoader;

    @Override
    public Provider current() {
        User current = userLoader.current();
        return providerRepository.findByUser_Id(current.getId()).
                orElseThrow(() -> new AuthorizationServiceException(ExceptionMessageCode.INVALID_PROVIDER_AUTHORIZATION));
    }
}
