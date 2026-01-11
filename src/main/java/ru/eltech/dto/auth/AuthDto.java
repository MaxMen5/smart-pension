package ru.eltech.dto.auth;

public record AuthDto(
        String role,
        String token
) {}