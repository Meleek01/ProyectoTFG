package org.socialgame.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
@Data // <--- Esta anotación es la que crea los métodos set/get automáticamente
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Fíjate que el nombre sea 'description' para que funcione 'setDescription'
    private String description;

    private String mediaUrl;

    private String mediaS3Key;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime createdAt = LocalDateTime.now();
}