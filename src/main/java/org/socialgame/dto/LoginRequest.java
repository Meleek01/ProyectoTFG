package org.socialgame.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequest {
    // Getters y Setters obligatorios
    private String username;
    private String password;

    // Constructor vacío obligatorio para Jackson
    public LoginRequest() {
    }

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

}