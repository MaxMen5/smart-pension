package ru.eltech.dto;

import lombok.Getter;

@Getter
public class AuthResponse {
    private final boolean success;
    private final String message;
    private final String role;
    private final String username;

    public AuthResponse(boolean success, String message, String role, String username) {
        this.success = success;
        this.message = message;
        this.role = role;
        this.username = username;
    }

}