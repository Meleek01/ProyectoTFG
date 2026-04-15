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
        // 1. Buscar al usuario por username (como definimos en el LoginRequest de Kotlin)
        User user = userRepository.findByUsername(request.getUsername());

        // 2. Verificar si el usuario existe y la contraseña coincide
        if (user != null && passwordEncoder.matches(request.getPassword(), user.getPassword())) {

            // 3. Generar el token JWT
            String token = jwtService.generateToken(user.getUsername());

            // Enviamos un JSON con estructura clara para Kotlin
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("mensaje", "¡Login Correcto! Bienvenido.");
            response.put("success", true);

            return ResponseEntity.ok(response);
        }

        // 4. Si falla, devolvemos 401 (Unauthorized) con mensaje de error
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("token", null);
        errorResponse.put("mensaje", "Usuario o contraseña incorrectos");
        errorResponse.put("success", false);

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        // Encriptar la contraseña antes de guardar en la base de datos
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok("Usuario registrado con éxito");
    }
}