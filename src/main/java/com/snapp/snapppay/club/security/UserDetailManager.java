package com.snapp.snapppay.club.security;

import com.snapp.snapppay.club.domain.entity.User;
import com.snapp.snapppay.club.exception.ExceptionMessageCode;
import com.snapp.snapppay.club.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDetailManager implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).
                orElseThrow(() -> new UsernameNotFoundException(ExceptionMessageCode.PRINCIPAL_IS_NOT_VALID));
        return new UserPrincipal(user);
    }
}
