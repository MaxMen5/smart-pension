package ru.eltech.dto.tasks;

public record TaskStatusDto(
        Long id,
        String title,
        String description,
        Boolean isCompleted,
        Long completedByWorkerId,
        String completedByWorkerLogin
) {}