package org.socialgame.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    // La clave debe ser un String largo. He puesto uno aleatorio seguro.
    private static final String SECRET_STRING = "esta_es_una_clave_muy_larga_y_segura_para_el_tfg_socialgame_2026";
    private final SecretKey key = Keys.hmacShaKeyFor(SECRET_STRING.getBytes());

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24 horas
                .signWith(key, SignatureAlgorithm.HS256) // Forma moderna de firmar
                .compact();
    }
}