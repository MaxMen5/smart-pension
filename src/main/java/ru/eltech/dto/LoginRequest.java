package ru.eltech.dto;

public record LoginRequest(
        String login,
        String password
) {}