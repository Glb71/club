package service.provider;

import com.snapp.snapppay.club.domain.entity.Provider;
import com.snapp.snapppay.club.domain.entity.User;
import com.snapp.snapppay.club.repository.ProviderRepository;
import com.snapp.snapppay.club.service.provider.ProviderLoader;
import com.snapp.snapppay.club.service.provider.ProviderLoaderImpl;
import com.snapp.snapppay.club.service.user.UserLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AuthorizationServiceException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProviderLoaderTest {

    private ProviderLoader providerLoader;
    @Mock
    private UserLoader userLoader;
    @Mock
    private ProviderRepository providerRepository;

    @BeforeEach
    void setup() {
        providerLoader = new ProviderLoaderImpl(providerRepository, userLoader);
    }

    @Test
    void testCurrentProvider() {
        when(userLoader.current()).thenReturn(new User());
        when(providerRepository.findByUser_Id(any())).thenReturn(Optional.empty());
        assertThrows(AuthorizationServiceException.class, providerLoader::current);
        when(providerRepository.findByUser_Id(any())).thenReturn(Optional.of(new Provider()));
        assertDoesNotThrow(() -> providerLoader.current());
    }

}
