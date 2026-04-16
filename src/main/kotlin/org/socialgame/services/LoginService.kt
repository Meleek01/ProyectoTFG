package org.socialgame.services

import org.springframework.stereotype.Service

class LoginService {


    @Service
    class LoginService {
        fun verificarUsuario(user: String, pass: String): Boolean {
            // Logica bbdd
            return user == "UTFG1347" && pass == "tfg-master123"
        }
    }
}