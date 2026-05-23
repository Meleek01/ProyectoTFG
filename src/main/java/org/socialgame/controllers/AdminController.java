package org.socialgame.controllers;

import org.socialgame.entities.Role;
import org.socialgame.entities.User;
import org.socialgame.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    // ── GET /api/admin/users → listar todos los usuarios ─────────────
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    // ── PUT /api/admin/users/{id}/role → cambiar rol ──────────────────
    // Body: { "role": "ROLE_ADMIN" } o { "role": "ROLE_USER" }
    @PutMapping("/users/{id}/role")
    public ResponseEntity<?> cambiarRol(@PathVariable Long id, @RequestBody Map<String, String> body) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        try {
            Role nuevoRol = Role.valueOf(body.get("role"));
            user.setRole(nuevoRol);
            userRepository.save(user);
            return ResponseEntity.ok("Rol actualizado a " + nuevoRol);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Rol inválido. Usa ROLE_USER o ROLE_ADMIN");
        }
    }

    // ── DELETE /api/admin/users/{id} → eliminar usuario ──────────────
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        userRepository.deleteById(id);
        return ResponseEntity.ok("Usuario eliminado");
    }
}