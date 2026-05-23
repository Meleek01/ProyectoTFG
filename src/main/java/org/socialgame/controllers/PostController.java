package org.socialgame.controllers;

import org.socialgame.entities.Post;
import org.socialgame.entities.User;
import org.socialgame.repositories.PostRepository;
import org.socialgame.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "*")
public class PostController {

    @Autowired private S3Service s3Service;
    @Autowired private PostRepository postRepository;
    @Autowired private UserRepository userRepository;

    @PostMapping("/upload")
    public ResponseEntity<?> createPost(
            @RequestParam("file") MultipartFile file,
            @RequestParam("username") String username,
            @RequestParam("description") String description) {

        User user = userRepository.findByUsername(username);
        if (user == null) return ResponseEntity.status(404).body("Usuario no encontrado");

        try {
            // 1. Subir imagen a AWS S3
            String key = s3Service.uploadFile(file);
            String url = s3Service.getFileUrl(key);

            // 2. Guardar Post en MySQL
            Post post = new Post();
            post.setDescription(description);
            post.setMediaUrl(url);
            post.setMediaS3Key(key);
            post.setUser(user);
            postRepository.save(post);

            // 3. Lógica de Gamificación
            user.setPoints(user.getPoints() + 10); // Puntos ganados
            user.setXp(user.getXp() + 50);         // Experiencia ganada

            // Subida de nivel automática (cada 500 XP)
            if (user.getXp() >= user.getLevel() * 500) {
                user.setLevel(user.getLevel() + 1);
            }

            userRepository.save(user);
            return ResponseEntity.ok(post);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error en S3: " + e.getMessage());
        }
    }

    @GetMapping
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
}