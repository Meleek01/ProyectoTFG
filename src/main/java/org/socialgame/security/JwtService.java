package org.socialgame.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    // 1. La clave DEBE tener al menos 32 caracteres para HS256.
    // He alargado tu clave para que cumpla el estándar de seguridad.
    private static final String SECRET_STRING = "tu_clave_super_secreta_para_el_proyecto_de_dam_2026_seguridad_extra";

    // 2. Generamos el objeto Key correctamente
    private SecretKey getSigningKey() {
        byte[] keyBytes = SECRET_STRING.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24 horas
                // 3. Usamos el nuevo método signWith que acepta el objeto Key
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}