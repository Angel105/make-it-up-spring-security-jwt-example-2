package com.makeitup.springsecurityjwtexample2.controller;

import com.makeitup.springsecurityjwtexample2.entity.Role;
import com.makeitup.springsecurityjwtexample2.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping("/role")
    public Role createNewRole(@RequestBody Role role) {
        return roleService.createNewRole(role);
    }
}
