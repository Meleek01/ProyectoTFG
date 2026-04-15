package org.socialgame.controllers;

import org.socialgame.dto.AuthResponse;
import org.socialgame.dto.LoginRequest; // <--- Importamos tu clase de Kotlin
import org.socialgame.repositories.UserRepository;
import org.socialgame.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.socialgame.entities.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*; // Simplificado para incluir CrossOrigin

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // Crucial para conectar con Jetpack Compose y AWS después
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        // 1. Buscar al usuario
        // Kotlin genera "getUsername()" automáticamente para la propiedad "username"
        User user = userRepository.findByUsername(request.getUsername());

        // 2. Verificar contraseña (usando el encoder)
        // Kotlin genera "getPassword()" automáticamente
        if (user != null && passwordEncoder.matches(request.getPassword(), user.getPassword())) {

            // 3. Generar el token si es correcto
            String token = jwtService.generateToken(user.getUsername());
            return ResponseEntity.ok(new AuthResponse(token));
        }

        // 4. Manejo de error si falla
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        // Encriptar la contraseña antes de guardar
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok("Usuario registrado con éxito");
    }
}