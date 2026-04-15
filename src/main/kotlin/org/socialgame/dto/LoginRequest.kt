package org.socialgame.dto

class LoginRequest {
    var password: CharSequence = TODO("initialize me")
    var username: String = TODO("initialize me")

    data class LoginRequest(
        val username: String = "",
        val password: String = ""
    )
}