package org.socialgame.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre completo del usuario
    private String name;

    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(min = 1, max = 20)
    @Column(unique = true)
    private String username;

    @Email(message = "Formato de email inválido")
    @NotBlank
    @Column(unique = true)
    private String email;

    @JsonIgnore
    @NotBlank
    private String password;

    // --- AWS S3 ---
    private String avatarUrl;
    private String avatarS3Key;

    // --- Gamificación ---
    private int xp = 0;
    private int xpNext = 500;   // umbral para subir de nivel (escala x1.4)
    private int level = 1;
    private int coins = 0;      // monedas virtuales (antes "points")

    // --- Estadísticas ---
    private int logins = 0;
    private int totalCalories = 0;
    private int totalTrainings = 0;

    // Actividad por día de la semana (7 enteros, índices 0=Dom → 6=Sáb)
    @ElementCollection
    @CollectionTable(name = "user_weekly_activity", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "activity")
    private List<Integer> weeklyActivity = new ArrayList<>(Collections.nCopies(7, 0));

    // --- Seguridad y Roles ---
    private Role role = Role.ROLE_USER;

    // --- Auditoría ---
    private LocalDateTime createdAt = LocalDateTime.now();
}