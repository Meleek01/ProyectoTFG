package org.socialgame;

import org.socialgame.entities.User;
import org.socialgame.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeed implements CommandLineRunner {

    private final UserRepository userRepository;

    public DataSeed(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            User testUser = new User();
            testUser.setUsername("GamerPro");
            testUser.setEmail("pro@test.com");
            testUser.setPassword("1234"); // En el futuro usaremos BCrypt
            testUser.setXp(100);
            testUser.setLevel(1);

            userRepository.save(testUser);
            System.out.println(">> Datos de prueba insertados: GamerPro");
        }
    }
}