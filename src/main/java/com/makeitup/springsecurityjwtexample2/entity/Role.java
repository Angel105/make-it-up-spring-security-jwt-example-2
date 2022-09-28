package com.makeitup.springsecurityjwtexample2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Role {
    @Id
    private String roleName;
    private String roleDescription;

}
