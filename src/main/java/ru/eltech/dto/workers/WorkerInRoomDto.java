package ru.eltech.dto.workers;

public record WorkerInRoomDto(
        Long id,
        String login,
        String shift
) {}
