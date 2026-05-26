package org.socialgame.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretString;

    private SecretKey getSigningKey() {
        byte[] keyBytes = secretString.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // ── Generar token ─────────────────────────────────────────────────
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24h
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // ── Extraer claims ────────────────────────────────────────────────
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // ── Extraer username del token ────────────────────────────────────
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // ── Validar token ─────────────────────────────────────────────────
    public boolean isTokenValid(String token, String username) {
        try {
            String extractedUsername = extractUsername(token);
            boolean notExpired = extractAllClaims(token).getExpiration().after(new Date());
            return extractedUsername.equals(username) && notExpired;
        } catch (Exception e) {
            return false;
        }
    }
}