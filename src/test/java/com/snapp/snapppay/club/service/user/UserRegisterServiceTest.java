package com.snapp.snapppay.club.service.user;

import com.snapp.snapppay.club.domain.entity.User;
import com.snapp.snapppay.club.domain.entity.UserRole;
import com.snapp.snapppay.club.domain.request.UserRegisterRequest;
import com.snapp.snapppay.club.enums.Roles;
import com.snapp.snapppay.club.repository.UserRepository;
import com.snapp.snapppay.club.validation.UserRegisterRequestValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserRegisterServiceTest {

    private UserRegisterService userRegisterService;
    @Mock
    private UserRegisterRequestValidator userRegisterRequestValidator;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserRepository userRepository;
    @Captor
    private ArgumentCaptor<User> captor;

    @BeforeEach
    void setup() {
        userRegisterService = new UserRegisterServiceImpl(userRegisterRequestValidator, passwordEncoder, userRepository);
        when(passwordEncoder.encode(any())).thenReturn("encoded-password");
    }

    @Test
    void testRegisterUser() {
        userRegisterService.register(new UserRegisterRequest());
        verify(userRepository).save(captor.capture());
        User registeredUser = captor.getValue();
        assertNotNull(registeredUser.getRoles(), "role not assigned");
        assertEquals(1, registeredUser.getRoles().size(), "initial role size must be one");
        Roles role = registeredUser.getRoles().stream().map(UserRole::getRole).findFirst().get();
        assertEquals(Roles.USER, role, "default role must be user");
        assertTrue(registeredUser.getActive(), "user must be active on register");
        assertEquals("encoded-password", registeredUser.getPassword());
    }

}