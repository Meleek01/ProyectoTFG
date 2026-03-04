package org.socialgame.controllers;

import org.socialgame.entities.User;
import org.socialgame.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // Obtener todos los usuarios
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Crear un usuario (Para probar el registro)
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }
}