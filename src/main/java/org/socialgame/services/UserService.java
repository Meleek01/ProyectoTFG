package org.socialgame.services;

import org.socialgame.entities.User;
import org.socialgame.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User ganarPuntos(Long userId, int puntosExtra) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        user.setPoints(user.getPoints() + puntosExtra);

        // Lógica de nivel simple: cada 500 puntos sube de nivel
        user.setLevel((user.getPoints() / 500) + 1);

        return userRepository.save(user);
    }
}
