package com.makeitup.springsecurityjwtexample2.dao;

import com.makeitup.springsecurityjwtexample2.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends JpaRepository<Role, String> {
}
