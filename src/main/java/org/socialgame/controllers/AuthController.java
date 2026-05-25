package org.socialgame.controllers;

import jakarta.validation.Valid;
import org.socialgame.dto.AuthResponse;
import org.socialgame.dto.LoginRequest;
import org.socialgame.dto.RegisterRequest;
import org.socialgame.entities.User;
import org.socialgame.repositories.UserRepository;
import org.socialgame.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
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
        User user = userRepository.findByUsername(request.getUsername());

        if (user == null) {
            return ResponseEntity.ok(new AuthResponse(null, "Usuario no encontrado", false));
        }

        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            String token = jwtService.generateToken(user.getUsername());
            return ResponseEntity.ok(new AuthResponse(token, "Login correcto", true));
        }

        return ResponseEntity.ok(new AuthResponse(null, "Contraseña incorrecta", false));
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()) != null) {
            return ResponseEntity.badRequest().body("El usuario ya existe");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok("Usuario registrado con éxito");
    }
}