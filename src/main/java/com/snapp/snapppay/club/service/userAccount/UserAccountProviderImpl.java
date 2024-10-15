package com.snapp.snapppay.club.service.userAccount;

import com.snapp.snapppay.club.domain.entity.Provider;
import com.snapp.snapppay.club.domain.entity.User;
import com.snapp.snapppay.club.domain.entity.UserAccount;
import com.snapp.snapppay.club.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAccountProviderImpl implements UserAccountProvider {

    private final UserAccountRepository userAccountRepository;

    @Override
    public UserAccount findOrCreate(Provider provider, User user) {
        return userAccountRepository.findByProvider_idAndUser_Id(provider.getId(), user.getId()).
                orElseGet(() -> create(provider, user));
    }

    private UserAccount create(Provider provider, User user) {
        UserAccount userAccount = new UserAccount();
        userAccount.setProvider(provider);
        userAccount.setUser(user);
        userAccountRepository.save(userAccount);
        return userAccount;
    }
}
