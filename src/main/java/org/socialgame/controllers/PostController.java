package org.socialgame.controllers;

import org.socialgame.entities.Post;
import org.socialgame.entities.User;
import org.socialgame.repositories.PostRepository;
import org.socialgame.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/{username}")
    public ResponseEntity<?> createPost(@PathVariable String username, @RequestBody String content) {
        User user = userRepository.findByUsername(username);
        if (user == null) return ResponseEntity.notFound().build();

        Post post = new Post();
        post.setContent(content);
        post.setAuthor(user);

        // Gamificación: ¡Ganar 10 puntos por publicar!
        user.setPoints(user.getPoints() + 10);
        userRepository.save(user);

        return ResponseEntity.ok(postRepository.save(post));
    }
}
