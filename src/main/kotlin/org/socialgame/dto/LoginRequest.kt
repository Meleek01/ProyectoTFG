package org.socialgame.dto

class LoginRequest {
    // Getters y Setters obligatorios
    var username: String? = null
    var password: String? = null

    // Constructor vacío obligatorio para Jackson
    constructor()

    constructor(username: String?, password: String?) {
        this.username = username
        this.password = password
    }
}