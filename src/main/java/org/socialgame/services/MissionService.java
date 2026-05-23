package org.socialgame.services;

import org.socialgame.entities.Mission;
import org.socialgame.entities.User;
import org.socialgame.repositories.MissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MissionService {

    @Autowired
    private MissionRepository missionRepository;

    @Autowired
    private UserService userService;

    // ── Obtener misiones del usuario ─────────────────────────────────
    public List<Mission> getMisiones(String username) {
        User user = userService.getByUsername(username);
        return missionRepository.findByUser(user);
    }

    // ── Crear misión ─────────────────────────────────────────────────
    public Mission crearMision(String username, Mission mission) {
        User user = userService.getByUsername(username);
        mission.setUser(user);
        mission.setStatus("active");
        mission.setProgress(0);
        return missionRepository.save(mission);
    }

    // ── Actualizar progreso ──────────────────────────────────────────
    public Mission actualizarProgreso(String username, Long missionId, int incremento) {
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new RuntimeException("Misión no encontrada"));

        // Verificar que la misión pertenece al usuario
        if (!mission.getUser().getUsername().equals(username)) {
            throw new RuntimeException("No autorizado");
        }

        // No modificar misiones ya completadas
        if ("completed".equals(mission.getStatus())) {
            return mission;
        }

        int nuevoProgreso = Math.max(0, Math.min(mission.getProgress() + incremento, mission.getGoal()));
        mission.setProgress(nuevoProgreso);

        // Completar misión si alcanza el objetivo
        if (nuevoProgreso >= mission.getGoal()) {
            mission.setStatus("completed");
            // Otorgar recompensas al usuario
            userService.completarMision(
                    mission.getUser().getId(),
                    mission.getXpReward(),
                    mission.getCoinReward(),
                    0
            );
        }

        return missionRepository.save(mission);
    }

    // ── Eliminar misión ──────────────────────────────────────────────
    public void eliminarMision(String username, Long missionId) {
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new RuntimeException("Misión no encontrada"));

        if (!mission.getUser().getUsername().equals(username)) {
            throw new RuntimeException("No autorizado");
        }

        missionRepository.delete(mission);
    }
}