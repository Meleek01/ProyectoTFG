package org.socialgame.repositories;

import org.socialgame.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Aquí Spring ya te da el CRUD básico (save, findAll, delete)
    User findByUsername(String username);
}

