package org.socialgame.services;

import org.socialgame.entities.Mission;
import org.socialgame.entities.User;
import org.socialgame.repositories.MissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

@Service
public class MissionService {

    private static final int MAX_MISIONES_DIA = 5;

    @Autowired
    private MissionRepository missionRepository;

    @Autowired
    private UserService userService;

    // Misiones predefinidas del sistema por día de la semana
    private Mission crearMisionSistema(User user, DayOfWeek dow) {
        Mission m = new Mission();
        m.setUser(user);
        m.setStatus("active");
        m.setProgress(0);
        m.setClaimed(false);
        m.setPredefined(true);
        m.setCreatedDate(LocalDate.now());

        switch (dow) {
            case MONDAY    -> { m.setTitle("Press de Banca");    m.setDescription("Completa 3 series de 10"); m.setIcon("💪"); m.setColorHex("#7C3AED"); m.setGoal(3);   m.setUnit("series"); m.setXpReward(400); m.setCoinReward(40); m.setCategory("fuerza"); }
            case TUESDAY   -> { m.setTitle("Carrera 5km");       m.setDescription("Corre sin parar");         m.setIcon("🏃"); m.setColorHex("#F59E0B"); m.setGoal(5);   m.setUnit("km");     m.setXpReward(600); m.setCoinReward(60); m.setCategory("cardio"); }
            case WEDNESDAY -> { m.setTitle("Yoga Flow");         m.setDescription("Estiramientos suaves");    m.setIcon("🧘"); m.setColorHex("#06B6D4"); m.setGoal(15);  m.setUnit("min");    m.setXpReward(350); m.setCoinReward(35); m.setCategory("mente");  }
            case THURSDAY  -> { m.setTitle("Boxeo Sombra");      m.setDescription("10 min de cardio");        m.setIcon("🥊"); m.setColorHex("#EF4444"); m.setGoal(10);  m.setUnit("min");    m.setXpReward(500); m.setCoinReward(50); m.setCategory("cardio"); }
            case FRIDAY    -> { m.setTitle("HIIT Intenso");      m.setDescription("20 min intervalos");       m.setIcon("⚡"); m.setColorHex("#F59E0B"); m.setGoal(20);  m.setUnit("min");    m.setXpReward(700); m.setCoinReward(70); m.setCategory("cardio"); }
            case SATURDAY  -> { m.setTitle("Gran Ruta");         m.setDescription("10km campo");              m.setIcon("🌲"); m.setColorHex("#10B981"); m.setGoal(10);  m.setUnit("km");     m.setXpReward(1000);m.setCoinReward(100);m.setCategory("cardio"); }
            case SUNDAY    -> { m.setTitle("Relax Total");       m.setDescription("Meditación profunda");     m.setIcon("🧘"); m.setColorHex("#06B6D4"); m.setGoal(20);  m.setUnit("min");    m.setXpReward(400); m.setCoinReward(40); m.setCategory("mente");  }
        }
        return missionRepository.save(m);
    }

    public List<Mission> getMisiones(String username) {
        User user = userService.getByUsername(username);
        List<Mission> misiones = missionRepository.findByUser(user);

        // Si no hay misión del sistema para hoy, crearla
        LocalDate hoy = LocalDate.now();
        boolean tieneMisionSistemaHoy = misiones.stream()
                .anyMatch(m -> m.isPredefined() && hoy.equals(m.getCreatedDate()));

        if (!tieneMisionSistemaHoy) {
            DayOfWeek dow = hoy.getDayOfWeek();
            Mission misionSistema = crearMisionSistema(user, dow);
            misiones.add(misionSistema);
        }

        return misiones;
    }

    public Mission crearMision(String username, Mission mission) {
        User user = userService.getByUsername(username);

        // Contar misiones activas de hoy (no predefinidas)
        List<Mission> misiones = missionRepository.findByUser(user);
        LocalDate hoy = LocalDate.now();
        long misionesHoy = misiones.stream()
                .filter(m -> !m.isPredefined() && hoy.equals(m.getCreatedDate()))
                .count();

        if (misionesHoy >= MAX_MISIONES_DIA - 1) { // -1 porque la del sistema ya ocupa 1
            throw new RuntimeException("Has alcanzado el límite de " + MAX_MISIONES_DIA + " misiones por día");
        }

        mission.setUser(user);
        mission.setStatus("active");
        mission.setProgress(0);
        mission.setClaimed(false);
        mission.setCreatedDate(hoy);
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

        if (nuevoProgreso >= mission.getGoal()) {
            mission.setStatus("completed");
        }

        return missionRepository.save(mission);
    }

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

        userService.completarMision(
                mission.getUser().getId(),
                mission.getXpReward(),
                mission.getCoinReward(),
                0
        );

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

        // No se pueden eliminar misiones del sistema
        if (mission.isPredefined()) {
            throw new RuntimeException("No puedes eliminar misiones del sistema");
        }

        missionRepository.delete(mission);
    }
}