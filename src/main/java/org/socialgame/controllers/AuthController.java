package org.socialgame.controllers;

import org.socialgame.dto.AuthResponse;
import org.socialgame.dto.LoginRequest;
import org.socialgame.repositories.UserRepository;
import org.socialgame.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.socialgame.entities.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
// Permitimos que Android conecte sin bloqueos de seguridad CORS
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        // LOG 1: ¿Qué está llegando del emulador?
        System.out.println("DEBUG: Intento de login con usuario: [" + request.getUsername() + "]");
        System.out.println("DEBUG: Contraseña recibida: [" + request.getPassword() + "]");

        User user = userRepository.findByUsername(request.getUsername());

        if (user == null) {
            System.out.println("DEBUG: El usuario no existe en la DB");
            return ResponseEntity.ok(new AuthResponse(null, "Usuario no encontrado", false));
        }

        // LOG 2: ¿Qué hay en la DB?
        System.out.println("DEBUG: Usuario encontrado. Hash en DB: " + user.getPassword());

        boolean matches = passwordEncoder.matches(request.getPassword(), user.getPassword());
        System.out.println("DEBUG: ¿La contraseña coincide?: " + matches);

        if (matches) {
            String token = jwtService.generateToken(user.getUsername());
            return ResponseEntity.ok(new AuthResponse(token, "¡Login Correcto!", true));
        }

        return ResponseEntity.ok(new AuthResponse(null, "Contraseña incorrecta", false));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        // Encriptar la contraseña antes de guardar en la base de datos
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok("Usuario registrado con éxito");
    }
}