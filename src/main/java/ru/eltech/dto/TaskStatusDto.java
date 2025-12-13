package ru.eltech.dto;

public record TaskStatusDto(
        Long id,
        String title,
        String description,
        Boolean isCompleted,
        Long completedByWorkerId,
        String completedByWorkerLogin
) {}