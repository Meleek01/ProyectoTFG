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
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MissionController {

    @Autowired
    private MissionService missionService;

    // ── GET /api/missions → listar misiones del usuario ──────────────
    @GetMapping
    public ResponseEntity<List<Mission>> getMisiones(Authentication auth) {
        return ResponseEntity.ok(missionService.getMisiones(auth.getName()));
    }

    // ── POST /api/missions → crear misión ────────────────────────────
    @PostMapping
    public ResponseEntity<Mission> crearMision(Authentication auth, @RequestBody Mission mission) {
        return ResponseEntity.ok(missionService.crearMision(auth.getName(), mission));
    }

    // ── PATCH /api/missions/{id}/progress → actualizar progreso ──────
    // Body: { "increment": 1 } o { "increment": -1 }
    @PatchMapping("/{id}/progress")
    public ResponseEntity<Mission> actualizarProgreso(
            Authentication auth,
            @PathVariable Long id,
            @RequestBody Map<String, Integer> body) {
        int incremento = body.getOrDefault("increment", 1);
        return ResponseEntity.ok(missionService.actualizarProgreso(auth.getName(), id, incremento));
    }

    // ── DELETE /api/missions/{id} → eliminar misión ───────────────────
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMision(Authentication auth, @PathVariable Long id) {
        missionService.eliminarMision(auth.getName(), id);
        return ResponseEntity.noContent().build();
    }
}