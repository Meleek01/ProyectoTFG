package org.socialgame.repositories;

import org.socialgame.entities.Mission;
import org.socialgame.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MissionRepository extends JpaRepository<Mission, Long> {
    List<Mission> findByUser(User user);
    List<Mission> findByUserAndStatus(User user, String status);
}
