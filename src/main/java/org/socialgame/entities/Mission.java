package org.socialgame.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "missions")
@Data
public class Mission {
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore // Asegúrate de importar com.fasterxml.jackson.annotation.JsonIgnore
    private User user;


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

    // Si la recompensa ya fue recogida
    private boolean claimed = false;

    // Tipo: "normal" o "calories"
    private String type = "normal";

    // Prioridad: 1=alta, 2=media, 3=baja
    private int priority = 2;

    // Fecha límite
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate deadline;

    // Si es misión del sistema o del usuario
    private boolean predefined = false;

    // Fecha de creación
    private LocalDate createdDate = LocalDate.now();

    // Relación con el usuario dueño de la misión
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}