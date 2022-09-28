package com.makeitup.springsecurityjwtexample2.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    private final static String SECRET_KEY = "secretPhrasePushItUp20220928MakeItLongEnoughToComplyWithHS512AlgorithmSpecificationRFC7518Section3WhichStatesThatKeysUsedWithHS512MUSTHaveSizeGreaterOrEqualTo512bits"; // Illegal base64 characters: '-','_'
    private final static Long TOKEN_VALIDITY = 3600 * 5 * 1000L;

    public String getUserNameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);

    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        String userName = getUserNameFromToken(token);
        return ( userName.equals(userDetails.getUsername()) && !isTokenExpired(token) );
    }

    private Boolean isTokenExpired(String token) {
        final Date expirationDate = getExpirationDateFromToken(token);
        return expirationDate.before(new Date());
    }

    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY ))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();

    }
}
