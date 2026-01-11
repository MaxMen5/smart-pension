package ru.eltech.dto.users;

public record UserDto (
        Long id,
        String login,
        String roleUser
)  {}
