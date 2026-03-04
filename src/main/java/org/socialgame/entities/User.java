package org.socialgame.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data // Esto de Lombok te ahorra hacer Getters y Setters
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(min = 1, max = 20)
    @Column(unique = true)
    private String username;

    @Email(message = "Formato de email inválido")
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    // --- Atributos de Gamificación ---
    private int xp = 0;
    private int level = 1;
    @Min(value = 0, message = "Los puntos no pueden ser negativos")
    private int points;

    // Puedes añadir un rol para el punto de "Gestión de roles" de tu guía
    private String role = "USER";
}