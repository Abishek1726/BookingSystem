package com.test.bookingsystem.service;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;

@Service
public class JWTService {
    @Value("${app.secret_key}")
    String SECRET_KEY;

    public String generateToken(String subject, Long expiryTime) {
        Long now = System.currentTimeMillis();
        return Jwts.builder().setClaims(new HashMap<>()).setSubject(subject)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + expiryTime))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    //TODO: Should stop parsing JWT multiple times
    public String getSubject(String token) {
        return getClaims(token).getSubject();
    }

    public Boolean isValid(String token) {
        try {
            getClaims(token);
            return true;
        } catch (SignatureException | ExpiredJwtException | MalformedJwtException e) {
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

}
