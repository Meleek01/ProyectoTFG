package org.socialgame.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "missions")
@Data
public class Mission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String icon;
    private String colorHex;

    // Estado: "active" o "completed"
    private String status = "active";

    private int progress = 0;
    private int goal;
    private String unit;

    private int xpReward;
    private int coinReward;

    // Categoría: fuerza, cardio, salud, habito, mente
    private String category;

    // Relación con el usuario dueño de la misión
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
