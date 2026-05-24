package org.socialgame.services;

import org.socialgame.entities.User;
import org.socialgame.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public User getByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) throw new RuntimeException("Usuario no encontrado");
        return user;
    }

    public User updateProfile(Long userId, String name, String username, String email, String avatarUrl) {
        User user = getById(userId);
        if (name != null)      user.setName(name);
        if (username != null)  user.setUsername(username);
        if (email != null)     user.setEmail(email);
        if (avatarUrl != null) user.setAvatarUrl(avatarUrl);
        return userRepository.save(user);
    }

    // ── Registrar login — solo 1 por día ─────────────────────────────
    public User registrarLogin(Long userId) {
        User user = getById(userId);
        LocalDate hoy = LocalDate.now();

        // Si ya hizo login hoy, no contar de nuevo
        if (hoy.equals(user.getLastLoginDate())) {
            return user;
        }

        user.setLogins(user.getLogins() + 1);
        user.setLastLoginDate(hoy);
        incrementarActividadSemanal(user);
        return userRepository.save(user);
    }

    public User completarMision(Long userId, int xpReward, int coinReward, int calorias) {
        User user = getById(userId);

        user.setCoins(user.getCoins() + coinReward);
        user.setTotalTrainings(user.getTotalTrainings() + 1);
        user.setTotalCalories(user.getTotalCalories() + calorias);

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

    public User gastarMonedas(Long userId, int coste) {
        User user = getById(userId);
        if (user.getCoins() < coste) {
            throw new RuntimeException("Monedas insuficientes");
        }
        user.setCoins(user.getCoins() - coste);
        return userRepository.save(user);
    }

    private void incrementarActividadSemanal(User user) {
        int dow = LocalDateTime.now().getDayOfWeek().getValue();
        int index = dow % 7;
        List<Integer> actividad = user.getWeeklyActivity();
        actividad.set(index, actividad.get(index) + 1);
        user.setWeeklyActivity(actividad);
    }
}