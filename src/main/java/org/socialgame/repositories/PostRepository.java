package org.socialgame.repositories;

import org.socialgame.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.socialgame.entities.User;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByAuthorOrderByCreatedAtDesc(User author);
}
