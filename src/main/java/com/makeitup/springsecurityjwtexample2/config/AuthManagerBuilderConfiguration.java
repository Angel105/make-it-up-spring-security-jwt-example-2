package com.makeitup.springsecurityjwtexample2.config;

import com.makeitup.springsecurityjwtexample2.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthManagerBuilderConfiguration {

    private final /*UserDetailsService*/ JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthManagerBuilderConfiguration(JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(jwtService).passwordEncoder(passwordEncoder);
    }

}
