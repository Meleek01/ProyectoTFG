package org.socialgame.controllers;

import org.socialgame.entities.User;
import org.socialgame.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    @Autowired
    private UserService userService;

    // ── GET /api/users/me → perfil del usuario autenticado ──────────
    @GetMapping("/me")
    public ResponseEntity<User> getMe(Authentication auth) {
        User user = userService.getByUsername(auth.getName());
        return ResponseEntity.ok(user);
    }

    // ── PUT /api/users/me → editar perfil ────────────────────────────
    @PutMapping("/me")
    public ResponseEntity<User> updateMe(Authentication auth, @RequestBody Map<String, String> body) {
        User user = userService.getByUsername(auth.getName());
        User updated = userService.updateProfile(
                user.getId(),
                body.get("name"),
                body.get("username"),
                body.get("email"),
                body.get("avatarUrl")
        );
        return ResponseEntity.ok(updated);
    }

    // ── POST /api/users/me/login → registrar inicio de sesión ────────
    @PostMapping("/me/login")
    public ResponseEntity<User> registrarLogin(Authentication auth) {
        User user = userService.getByUsername(auth.getName());
        return ResponseEntity.ok(userService.registrarLogin(user.getId()));
    }

    // ── POST /api/users/me/complete-mission → completar misión ───────
    // Body: { "xpReward": 500, "coinReward": 50, "calories": 200 }
    @PostMapping("/me/complete-mission")
    public ResponseEntity<User> completarMision(Authentication auth, @RequestBody Map<String, Integer> body) {
        User user = userService.getByUsername(auth.getName());
        return ResponseEntity.ok(userService.completarMision(
                user.getId(),
                body.getOrDefault("xpReward", 0),
                body.getOrDefault("coinReward", 0),
                body.getOrDefault("calories", 0)
        ));
    }

    // ── POST /api/users/me/spend-coins → gastar monedas en tienda ────
    // Body: { "amount": 50 }
    @PostMapping("/me/spend-coins")
    public ResponseEntity<User> gastarMonedas(Authentication auth, @RequestBody Map<String, Integer> body) {
        User user = userService.getByUsername(auth.getName());
        return ResponseEntity.ok(userService.gastarMonedas(user.getId(), body.get("amount")));
    }
}