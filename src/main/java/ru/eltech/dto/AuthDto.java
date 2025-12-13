package ru.eltech.dto;

public record AuthDto(
        boolean success,
        String message,
        String role,
        String username,
        String token
) {}