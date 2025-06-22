package com.example.productservice.jwtservice;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;


@Service
public class JwtService {
    private final String SECRET_KEY="a-string-secret-at-least-256-bits-long";
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }
    public boolean validateToken(String token) {
        try{
            extractAllClaims(token);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigninKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigninKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public List<SimpleGrantedAuthority> extractRoles(String token) {
        Claims claims = extractAllClaims(token);
        Object rolesObj = claims.get("roles");

        if (rolesObj == null) {
            return List.of();
        }

        // Güvenli dönüşüm: rolesObj --> List<String>
        List<String> roles;
        if (rolesObj instanceof List<?>) {
            roles = ((List<?>) rolesObj).stream()
                    .map(Object::toString)
                    .toList();
        } else {
            roles = new ArrayList<>();
        }

        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

}
