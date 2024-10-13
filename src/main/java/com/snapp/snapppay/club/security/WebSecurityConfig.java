package com.snapp.snapppay.club.security;

import com.snapp.snapppay.club.security.jwt.JwtTokenVerifier;
import com.snapp.snapppay.club.security.jwt.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final UserDetailManager userDetailManager;
    private final AuthenticationConfiguration authenticationConfiguration;
    @Autowired
    private TokenService tokenService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new JwtTokenVerifier(tokenService), UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests.anyRequest().authenticated()
                );
        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailManager);
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
