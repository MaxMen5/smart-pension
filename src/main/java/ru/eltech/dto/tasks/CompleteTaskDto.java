package ru.eltech.dto.tasks;

public record CompleteTaskDto(
        String login,
        Boolean isCompleted
) {}