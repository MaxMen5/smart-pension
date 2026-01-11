package ru.eltech.dto.auth;

public record LoginDto(
        String login,
        String password
) {}