package org.socialgame.services;

import org.socialgame.entities.User;
import org.socialgame.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // ── Obtener usuario por ID ───────────────────────────────────────
    public User getById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    // ── Obtener usuario por username (para JWT) ──────────────────────
    public User getByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) throw new RuntimeException("Usuario no encontrado");
        return user;
    }

    // ── Actualizar perfil ────────────────────────────────────────────
    public User updateProfile(Long userId, String name, String username, String email, String avatarUrl) {
        User user = getById(userId);
        if (name != null)      user.setName(name);
        if (username != null)  user.setUsername(username);
        if (email != null)     user.setEmail(email);
        if (avatarUrl != null) user.setAvatarUrl(avatarUrl);
        return userRepository.save(user);
    }

    // ── Registrar login ──────────────────────────────────────────────
    public User registrarLogin(Long userId) {
        User user = getById(userId);
        user.setLogins(user.getLogins() + 1);
        incrementarActividadSemanal(user);
        return userRepository.save(user);
    }

    // ── Completar misión (XP + coins + estadísticas) ─────────────────
    public User completarMision(Long userId, int xpReward, int coinReward, int calorias) {
        User user = getById(userId);

        user.setCoins(user.getCoins() + coinReward);
        user.setTotalTrainings(user.getTotalTrainings() + 1);
        user.setTotalCalories(user.getTotalCalories() + calorias);
        incrementarActividadSemanal(user);

        // Curva exponencial x1.4
        int nuevoXp = user.getXp() + xpReward;
        int nivel = user.getLevel();
        int umbral = user.getXpNext();

        while (nuevoXp >= umbral) {
            nuevoXp -= umbral;
            nivel += 1;
            umbral = (int) Math.floor(umbral * 1.4);
        }

        user.setXp(nuevoXp);
        user.setLevel(nivel);
        user.setXpNext(umbral);

        return userRepository.save(user);
    }

    // ── Gastar monedas ───────────────────────────────────────────────
    public User gastarMonedas(Long userId, int coste) {
        User user = getById(userId);
        if (user.getCoins() < coste) {
            throw new RuntimeException("Monedas insuficientes");
        }
        user.setCoins(user.getCoins() - coste);
        return userRepository.save(user);
    }

    // ── Actividad semanal (0=Dom, 6=Sáb) ────────────────────────────
    private void incrementarActividadSemanal(User user) {
        int dow = LocalDateTime.now().getDayOfWeek().getValue(); // 1=Lun..7=Dom
        int index = dow % 7; // 0=Dom, 1=Lun, ..., 6=Sáb
        List<Integer> actividad = user.getWeeklyActivity();
        actividad.set(index, actividad.get(index) + 1);
        user.setWeeklyActivity(actividad);
    }
}
