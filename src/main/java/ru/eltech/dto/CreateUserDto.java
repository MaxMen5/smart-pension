package ru.eltech.dto;

public record CreateUserDto(
        String login,
        String passUser,
        String roleUser
) {}
