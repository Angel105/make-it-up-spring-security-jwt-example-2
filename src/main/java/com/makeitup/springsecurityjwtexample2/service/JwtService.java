package com.makeitup.springsecurityjwtexample2.service;

import com.makeitup.springsecurityjwtexample2.dao.UserDao;
import com.makeitup.springsecurityjwtexample2.dto.JwtRequest;
import com.makeitup.springsecurityjwtexample2.dto.JwtResponse;
import com.makeitup.springsecurityjwtexample2.entity.User;
import com.makeitup.springsecurityjwtexample2.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class JwtService implements UserDetailsService {

    private final UserDao userDao;

    private final JwtUtil jwtUtil;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public JwtService(UserDao userDao, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userDao = userDao;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception {
        String username = jwtRequest.getUserName();
        String password = jwtRequest.getPassword();
        authenticate(username, password);

        final UserDetails userDetails = loadUserByUsername(username);

        String newlyGeneratedToken = jwtUtil.generateToken(userDetails);

        User user = userDao.findById(username).get();

        return new JwtResponse(user, newlyGeneratedToken);
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("User is disabled");
        } catch (BadCredentialsException e) {
            throw new Exception("Bad credentials from user");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findById(username).get();

        if (user != null) {
            return new org.springframework.security.core.userdetails.User(
                    user.getUserName(),
                    user.getPassword(),
                    getAuthorities(user)
            );
        } else {
            throw new UsernameNotFoundException("Username is not valid");

        }
    }

    private Set getAuthorities(User user) {
        Set authorities = new HashSet();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleName()));
        });

        return authorities;
    }

}
