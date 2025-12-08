package ru.eltech.dto;

public record UserDto (
        Long id,
        String login,
        String roleUser
)  {}
