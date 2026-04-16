package org.socialgame;

import org.socialgame.entities.User;
import org.socialgame.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeed implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeed(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            User testUser = new User();
            testUser.setUsername("GamerPro");
            testUser.setEmail("pro@test.com");
            // AHORA SÍ: Guardamos "1234" encriptado
            testUser.setPassword(passwordEncoder.encode("1234"));
            testUser.setXp(100);
            testUser.setLevel(1);
            testUser.setPoints(50);

            userRepository.save(testUser);
            System.out.println(">> DB AWS: Usuario GamerPro creado con BCrypt.");
        }
    }
}