package com.makeitup.springsecurityjwtexample2.config;

import com.makeitup.springsecurityjwtexample2.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // for role-based authentication
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private final JwtRequestFilter jwtFilter;

    // private final /*UserDetailsService*/JwtService jwtService;

    @Autowired
    public WebSecurityConfiguration(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                                    JwtRequestFilter jwtFilter
            // , /*UserDetailsService*/ JwtService jwtService
    ) {

        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtFilter = jwtFilter;
        // this.jwtService = jwtService;
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();//.disable();

        http.csrf().disable()
                .authorizeRequests().antMatchers("/authenticate", "/registerNewUser").permitAll()// dont authenticate this particular endpoint, other than that ...
                .antMatchers(HttpHeaders.ALLOW).permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
