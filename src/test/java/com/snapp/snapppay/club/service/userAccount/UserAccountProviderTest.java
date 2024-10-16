package com.snapp.snapppay.club.service.userAccount;

import com.snapp.snapppay.club.domain.entity.Provider;
import com.snapp.snapppay.club.domain.entity.User;
import com.snapp.snapppay.club.domain.entity.UserAccount;
import com.snapp.snapppay.club.repository.UserAccountRepository;
import com.snapp.snapppay.club.service.provider.ProviderLoader;
import com.snapp.snapppay.club.service.user.UserLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserAccountProviderTest {

    private UserAccountProvider userAccountProvider;
    private User user;
    private Provider provider;
    @Mock
    private UserLoader userLoader;
    @Mock
    private ProviderLoader providerLoader;
    @Mock
    private UserAccountRepository userAccountRepository;


    @BeforeEach
    void setup() {
        userAccountProvider = new UserAccountProviderImpl(userAccountRepository, providerLoader, userLoader);
        user = new User();
        user.setUsername("testUser");
        provider = new Provider();
        provider.setTitle("testProvider");
        when(providerLoader.current()).thenReturn(provider);
        when(userLoader.loadByUsername(any())).thenReturn(user);
    }

    @Test
    void testFind() {
        UserAccount repositoryUserAccount = new UserAccount();
        when(userAccountRepository.findByProvider_idAndUser_Id(any(), any())).thenReturn(Optional.of(repositoryUserAccount));
        UserAccount result = userAccountProvider.findOrCreate(user.getUsername());
        try {
            verify(userAccountRepository, times(0)).save(any(UserAccount.class));
        } catch (AssertionError e) {
            fail("should not create a new user account where user account exists in database");
        }
        assertNotNull(result, "user account should not be null");
        assertEquals(repositoryUserAccount, result,
                "if repository find a user account should not create a new one");
    }

    @Test
    void testCreate() {
        when(userAccountRepository.findByProvider_idAndUser_Id(any(), any())).thenReturn(Optional.empty());
        UserAccount result = userAccountProvider.findOrCreate(user.getUsername());
        try {
            verify(userAccountRepository, times(1)).save(any(UserAccount.class));
        } catch (AssertionError e) {
            fail("new user account must be saved when user account is not available");
        }
        assertNotNull(result, "user account should not be null");
        assertEquals(provider, result.getProvider(), "provider must be equals to found provider");
        assertEquals(user, result.getUser(), "user must be equals to found user");
    }

}
