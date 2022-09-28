package com.makeitup.springsecurityjwtexample2.dto;

import com.makeitup.springsecurityjwtexample2.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {

    private User user;
    private String jwtToken;
}
