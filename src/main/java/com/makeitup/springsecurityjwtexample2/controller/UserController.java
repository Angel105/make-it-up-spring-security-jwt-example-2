package com.makeitup.springsecurityjwtexample2.controller;

import com.makeitup.springsecurityjwtexample2.entity.User;
import com.makeitup.springsecurityjwtexample2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping({"/registerNewUser"})
    public User registerNewUser(@RequestBody User user) {
        return userService.registerNewUser(user);
    }

    @PostConstruct
    public void initRolesAndUsers() {
        userService.initRolesAndUsers();
    }

    @GetMapping({"/forAdmin"})
    @PreAuthorize("hasRole('Admin')")
    public String forAdmin() {
        return "This URL is only accessible to admin";
    }

    @GetMapping({"/forUser"})
    //@PreAuthorize("hasRole('User')")
    @PreAuthorize("hasAnyRole('Admin','User')")
    public String forUser() {
        return "This URL is only accessible to the user and admin";
    }
}
