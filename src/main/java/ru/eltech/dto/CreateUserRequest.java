package ru.eltech.dto;

public record CreateUserRequest (
        String login,
        String passUser,
        String roleUser
) {}
