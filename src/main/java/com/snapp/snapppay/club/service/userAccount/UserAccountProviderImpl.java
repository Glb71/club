package com.snapp.snapppay.club.service.userAccount;

import com.snapp.snapppay.club.domain.entity.Provider;
import com.snapp.snapppay.club.domain.entity.User;
import com.snapp.snapppay.club.domain.entity.UserAccount;
import com.snapp.snapppay.club.repository.UserAccountRepository;
import com.snapp.snapppay.club.service.provider.ProviderLoader;
import com.snapp.snapppay.club.service.user.UserLoader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAccountProviderImpl implements UserAccountProvider {

    private final UserAccountRepository userAccountRepository;
    private final ProviderLoader providerLoader;
    private final UserLoader userLoader;

    @Override
    public UserAccount findOrCreate(String username) {
        Provider provider = providerLoader.current();
        User user = userLoader.loadByUsername(username);
        return userAccountRepository.findByProvider_idAndUser_Id(provider.getId(), user.getId()).
                orElseGet(() -> create(provider, user));
    }

    private UserAccount create(Provider provider, User user) {
        UserAccount userAccount = UserAccount.builder().
                provider(provider).
                user(user).
                build();
        userAccountRepository.save(userAccount);
        return userAccount;
    }
}
