package com.uzinfocom.uzinfocomcontrol.untils;
import com.uzinfocom.uzinfocomcontrol.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Генерация токена (пока кладем только username)
    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUserName()) // username в subject
                .claim("id", user.getId())      // 👈 можно сразу userId
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 час
                .signWith(key)
                .compact();
    }

    // Получить все claims (данные из токена)
    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Получить username
    public String getUsernameFromToken(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    // Получить id
    public Long getUserIdFromToken(String token) {
        return getAllClaimsFromToken(token).get("id", Long.class);
    }
}


