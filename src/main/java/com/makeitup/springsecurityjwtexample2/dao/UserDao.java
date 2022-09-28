package com.makeitup.springsecurityjwtexample2.dao;

import com.makeitup.springsecurityjwtexample2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, String> {
}
