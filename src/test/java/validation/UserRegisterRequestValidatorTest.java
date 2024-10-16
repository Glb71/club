package validation;

import com.snapp.snapppay.club.domain.request.UserRegisterRequest;
import com.snapp.snapppay.club.exception.ValidationException;
import com.snapp.snapppay.club.repository.UserRepository;
import com.snapp.snapppay.club.validation.UserRegisterRequestValidator;
import com.snapp.snapppay.club.validation.UserRegisterRequestValidatorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserRegisterRequestValidatorTest {

    private UserRegisterRequestValidator userRegisterRequestValidator;
    private UserRegisterRequest userRegisterRequest;
    @Mock
    private UserRepository providerRepository;

    @BeforeEach
    void setup() {
        userRegisterRequestValidator = new UserRegisterRequestValidatorImpl(providerRepository);
        userRegisterRequest = new UserRegisterRequest();
        when(providerRepository.existsByUsername(userRegisterRequest.getUsername())).thenReturn(false);
        when(providerRepository.existsByNationalCode(userRegisterRequest.getNationalCode())).thenReturn(false);
    }

    @Test
    void checkUsernameValidation() {
        when(providerRepository.existsByUsername(userRegisterRequest.getUsername())).thenReturn(true);
        assertThrows(ValidationException.class, () -> userRegisterRequestValidator.validate(userRegisterRequest));
        when(providerRepository.existsByUsername(userRegisterRequest.getUsername())).thenReturn(false);
        assertDoesNotThrow(() -> userRegisterRequestValidator.validate(userRegisterRequest));
    }

    @Test
    void checkNationalCodeValidation() {
        when(providerRepository.existsByNationalCode(userRegisterRequest.getNationalCode())).thenReturn(true);
        assertThrows(ValidationException.class, () -> userRegisterRequestValidator.validate(userRegisterRequest));
        when(providerRepository.existsByNationalCode(userRegisterRequest.getNationalCode())).thenReturn(false);
        assertDoesNotThrow(() -> userRegisterRequestValidator.validate(userRegisterRequest));
    }

}
