package com.makeitup.springsecurityjwtexample2.service;

import com.makeitup.springsecurityjwtexample2.dao.RoleDao;
import com.makeitup.springsecurityjwtexample2.dao.UserDao;
import com.makeitup.springsecurityjwtexample2.entity.Role;
import com.makeitup.springsecurityjwtexample2.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerNewUser(User user) {
        Role generalRole = roleDao.findById("User").get();

        Set<Role> roles = new HashSet<>();
        roles.add(generalRole);
        user.setRoles(roles);

        user.setPassword(getEncodedPassword(user.getPassword()));

        return userDao.save(user);
    }

    public void initRolesAndUsers() {
        Role adminRole = new Role();
        adminRole.setRoleName("Admin");
        adminRole.setRoleDescription("Admin role of the app");
        if (!roleDao.existsById(adminRole.getRoleName())) {
            roleDao.save(adminRole);
        }

        Role userRole = new Role();
        userRole.setRoleName("User");
        userRole.setRoleDescription("Default role for a newly created user");
        if (!roleDao.existsById(userRole.getRoleName())) {
            roleDao.save(userRole);
        }

        User adminUser = new User();
        adminUser.setFirstName("Andrew");
        adminUser.setLastName("P");
        adminUser.setUserName("ponomara");
        adminUser.setPassword(getEncodedPassword("pwd1"));

        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        adminUser.setRoles(adminRoles);
        if (!userDao.existsById(adminUser.getUserName())) {
            userDao.save(adminUser);
        }

        User user = new User();
        user.setFirstName("Dimitri");
        user.setLastName("P");
        user.setUserName("ponomard");
        user.setPassword(getEncodedPassword("pwd2"));

        Set<Role> userRoles = new HashSet<>();
        userRoles.add(userRole);
        user.setRoles(userRoles);
        if (!userDao.existsById(user.getUserName())) {
            userDao.save(user);
        }

    }

    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
