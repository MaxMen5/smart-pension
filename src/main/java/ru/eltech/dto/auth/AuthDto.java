package ru.eltech.dto.auth;

public record AuthDto(
        boolean success,
        String message,
        String role,
        String username,
        String token
) {}