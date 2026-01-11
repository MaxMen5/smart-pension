package ru.eltech.dto.users;

public record CreateUserDto(
        String login,
        String passUser,
        String roleUser
) {}
