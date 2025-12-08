package ru.eltech.dto;

public record AuthResponse(
        boolean success,
        String message,
        String role,
        String username,
        String token
) {}