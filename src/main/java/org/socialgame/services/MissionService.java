package org.socialgame.services;

import org.socialgame.entities.Mission;
import org.socialgame.entities.User;
import org.socialgame.repositories.MissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MissionService {

    @Autowired
    private MissionRepository missionRepository;

    @Autowired
    private UserService userService;

    public List<Mission> getMisiones(String username) {
        User user = userService.getByUsername(username);
        return missionRepository.findByUser(user);
    }

    public Mission crearMision(String username, Mission mission) {
        User user = userService.getByUsername(username);
        mission.setUser(user);
        mission.setStatus("active");
        mission.setProgress(0);
        mission.setClaimed(false);
        return missionRepository.save(mission);
    }

    public Mission actualizarProgreso(String username, Long missionId, int incremento) {
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new RuntimeException("Misión no encontrada"));

        if (!mission.getUser().getUsername().equals(username)) {
            throw new RuntimeException("No autorizado");
        }

        if ("completed".equals(mission.getStatus())) {
            return mission;
        }

        int nuevoProgreso = Math.max(0, Math.min(mission.getProgress() + incremento, mission.getGoal()));
        mission.setProgress(nuevoProgreso);

        // Solo marca como completada, NO otorga recompensas todavía
        if (nuevoProgreso >= mission.getGoal()) {
            mission.setStatus("completed");
        }

        return missionRepository.save(mission);
    }

    // ── Reclamar recompensa ──────────────────────────────────────────
    public Map<String, Object> reclamarRecompensa(String username, Long missionId) {
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new RuntimeException("Misión no encontrada"));

        if (!mission.getUser().getUsername().equals(username)) {
            throw new RuntimeException("No autorizado");
        }

        if (!"completed".equals(mission.getStatus())) {
            throw new RuntimeException("La misión no está completada");
        }

        if (mission.isClaimed()) {
            throw new RuntimeException("La recompensa ya fue recogida");
        }

        // Otorgar recompensas
        userService.completarMision(
                mission.getUser().getId(),
                mission.getXpReward(),
                mission.getCoinReward(),
                0
        );

        // Marcar como reclamada
        mission.setClaimed(true);
        missionRepository.save(mission);

        return Map.of(
                "xpReward", mission.getXpReward(),
                "coinReward", mission.getCoinReward(),
                "message", "¡Recompensa recogida!"
        );
    }

    public void eliminarMision(String username, Long missionId) {
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new RuntimeException("Misión no encontrada"));

        if (!mission.getUser().getUsername().equals(username)) {
            throw new RuntimeException("No autorizado");
        }

        missionRepository.delete(mission);
    }
}