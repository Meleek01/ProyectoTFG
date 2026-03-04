package org.socialgame.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 280) // Al estilo Twitter/X
    private String content;

    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne // Muchos posts pertenecen a un solo usuario
    @JoinColumn(name = "user_id")
    private User author;
}