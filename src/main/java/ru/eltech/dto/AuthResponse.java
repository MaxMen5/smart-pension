package ru.eltech.dto;

import lombok.Getter;

@Getter
public class AuthResponse {
    private final boolean success;
    private final String message;
    private final String role;
    private final String username;
    private final String token;

    public AuthResponse(boolean success, String message, String role, String username, String token) {
        this.success = success;
        this.message = message;
        this.role = role;
        this.username = username;
        this.token = token;
    }

}