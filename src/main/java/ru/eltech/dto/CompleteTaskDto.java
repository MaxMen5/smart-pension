package ru.eltech.dto;

public record CompleteTaskDto(
        String login,
        Boolean isCompleted
) {}