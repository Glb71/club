package validation;

import com.snapp.snapppay.club.domain.request.ProductRegisterRequest;
import com.snapp.snapppay.club.exception.ValidationException;
import com.snapp.snapppay.club.repository.ProductRepository;
import com.snapp.snapppay.club.validation.ProductRegisterRequestValidator;
import com.snapp.snapppay.club.validation.ProductRegisterRequestValidatorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductRegisterRequestValidatorTest {

    private ProductRegisterRequestValidator productRegisterRequestValidator;
    private ProductRegisterRequest productRegisterRequest;
    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    void setup() {
        productRegisterRequestValidator = new ProductRegisterRequestValidatorImpl(productRepository);
        productRegisterRequest = new ProductRegisterRequest();
    }

    @Test
    void testTitleValidation() {
        when(productRepository.existsByTitle(productRegisterRequest.getTitle())).thenReturn(true);
        assertThrows(ValidationException.class, () -> productRegisterRequestValidator.validate(productRegisterRequest));
        when(productRepository.existsByTitle(productRegisterRequest.getTitle())).thenReturn(false);
        assertDoesNotThrow(() -> productRegisterRequestValidator.validate(productRegisterRequest));
    }

}
