package validation;

import com.snapp.snapppay.club.domain.request.ProviderRegisterRequest;
import com.snapp.snapppay.club.exception.ValidationException;
import com.snapp.snapppay.club.repository.ProviderRepository;
import com.snapp.snapppay.club.validation.ProviderRegisterRequestValidator;
import com.snapp.snapppay.club.validation.ProviderRegisterRequestValidatorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProviderRegisterRequestValidatorTest {

    private ProviderRegisterRequestValidator providerRegisterRequestValidator;
    private ProviderRegisterRequest providerRegisterRequest;
    @Mock
    private ProviderRepository providerRepository;

    @BeforeEach
    void setup() {
        providerRegisterRequestValidator = new ProviderRegisterRequestValidatorImpl(providerRepository);
        providerRegisterRequest = new ProviderRegisterRequest();
        when(providerRepository.existsByTitle(providerRegisterRequest.getTitle())).thenReturn(false);
        when(providerRepository.existsByUser_Id(providerRegisterRequest.getUserId())).thenReturn(false);
    }

    @Test
    void checkTitleValidation() {
        when(providerRepository.existsByTitle(providerRegisterRequest.getTitle())).thenReturn(true);
        assertThrows(ValidationException.class, () -> providerRegisterRequestValidator.validate(providerRegisterRequest));
        when(providerRepository.existsByTitle(providerRegisterRequest.getTitle())).thenReturn(false);
        assertDoesNotThrow(() -> providerRegisterRequestValidator.validate(providerRegisterRequest));
    }

    @Test
    void checkUserValidation() {
        when(providerRepository.existsByUser_Id(providerRegisterRequest.getUserId())).thenReturn(true);
        assertThrows(ValidationException.class, () -> providerRegisterRequestValidator.validate(providerRegisterRequest));
        when(providerRepository.existsByUser_Id(providerRegisterRequest.getUserId())).thenReturn(false);
        assertDoesNotThrow(() -> providerRegisterRequestValidator.validate(providerRegisterRequest));
    }

}
