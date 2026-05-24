package org.socialgame.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(min = 1, max = 20)
    @Column(unique = true)
    private String username;

    @Email(message = "Formato de email inválido")
    @NotBlank
    @Column(unique = true)
    private String email;

    @NotBlank
    private String password;

    private String avatarUrl;

    // --- Gamificación ---
    private int xp = 0;
    private int xpNext = 500;
    private int level = 1;
    private int coins = 0;

    // --- Estadísticas ---
    private int logins = 0;
    private int totalCalories = 0;
    private int totalTrainings = 0;

    @ElementCollection
    @CollectionTable(name = "user_weekly_activity", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "activity")
    private List<Integer> weeklyActivity = new ArrayList<>(Collections.nCopies(7, 0));

    // --- Anti-farmeo: último día de login ───────────────────────────
    private LocalDate lastLoginDate;

    // --- Seguridad ---
    @Enumerated(EnumType.STRING)
    private Role role = Role.ROLE_USER;

    // --- Auditoría ---
    private LocalDateTime createdAt = LocalDateTime.now();
}