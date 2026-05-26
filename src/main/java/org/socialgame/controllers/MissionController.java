package org.socialgame.controllers;

import org.socialgame.entities.Mission;
import org.socialgame.services.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/missions")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class MissionController {

    @Autowired
    private MissionService missionService;

    @GetMapping
    public ResponseEntity<List<Mission>> getMisiones(Authentication auth) {
        return ResponseEntity.ok(missionService.getMisiones(auth.getName()));
    }

    @PostMapping
    public ResponseEntity<Mission> crearMision(Authentication auth, @RequestBody Mission mission) {
        return ResponseEntity.ok(missionService.crearMision(auth.getName(), mission));

    }

    @PatchMapping("/{id}/progress")
    public ResponseEntity<Mission> actualizarProgreso(
            Authentication auth,
            @PathVariable Long id,
            @RequestBody Map<String, Integer> body) {
        int incremento = body.getOrDefault("increment", 1);
        return ResponseEntity.ok(missionService.actualizarProgreso(auth.getName(), id, incremento));
    }

    // ── POST /api/missions/{id}/claim → recoger recompensa ───────────
    @PostMapping("/{id}/claim")
    public ResponseEntity<?> reclamarRecompensa(Authentication auth, @PathVariable Long id) {
        return ResponseEntity.ok(missionService.reclamarRecompensa(auth.getName(), id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMision(Authentication auth, @PathVariable Long id) {
        missionService.eliminarMision(auth.getName(), id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/{id}")
    public ResponseEntity<?> editarMision(
            Authentication auth,
            @PathVariable Long id,
            @RequestBody Mission missionDetails) {

        System.out.println("DEBUG: Petición recibida para el ID: " + id);

        // 1. Validamos que la autenticación exista
        if (auth == null || auth.getName() == null) {
            System.out.println("ERROR: El usuario no está autenticado.");
            return ResponseEntity.status(401).body("Usuario no autenticado");
        }

        try {
            String username = auth.getName();
            Mission updatedMission = missionService.editarMision(username, id, missionDetails);
            return ResponseEntity.ok(updatedMission);
        } catch (Exception e) {
            System.out.println("ERROR en el servicio: " + e.getMessage());
            return ResponseEntity.status(500).body("Error al actualizar la misión: " + e.getMessage());
        }
    }
}