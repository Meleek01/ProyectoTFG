package org.socialgame;

import org.socialgame.entities.Role; // Asegúrate de haber creado el Enum
import org.socialgame.entities.User;
import org.socialgame.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeed implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Integer XP_INICIAL = 0;

    public DataSeed(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // Solo creamos usuarios si la tabla está vacía
        if (userRepository.count() == 0) {

            // 1. CREAR USUARIO TEST (GamerPro)
            User testUser = new User();
            testUser.setUsername("GamerPro");
            testUser.setEmail("pro@test.com");
            testUser.setPassword(passwordEncoder.encode("1234"));
            testUser.setXp(100);
            testUser.setLevel(1);
            testUser.setCoins(500);
            testUser.setRole(Role.ROLE_USER); // Rol normal
            // De momento S3 en null o una URL por defecto
            testUser.setAvatarUrl("https://ui-avatars.com/api/?name=GamerPro");

            userRepository.save(testUser);

            // 2. CREAR USUARIO ADMINISTRADOR
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@socialgame.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setXp(999);
            admin.setLevel(99);
            admin.setCoins(999);
            admin.setRole(Role.ROLE_ADMIN); // <--- ROL DE ADMINISTRADOR
            admin.setAvatarUrl("https://ui-avatars.com/api/?name=Admin");

            userRepository.save(admin);

            System.out.println(">> DB AWS: Usuarios iniciales (GamerPro y Admin) creados correctamente.");
        }
    }
}