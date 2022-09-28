package com.makeitup.springsecurityjwtexample2.service;

import com.makeitup.springsecurityjwtexample2.dao.RoleDao;
import com.makeitup.springsecurityjwtexample2.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleDao roleDao;

    public Role createNewRole(Role role) {
        return roleDao.save(role);
    }
}
