package org.socialgame.dto;

public record AuthResponse(String token, String mensaje, boolean success) { }