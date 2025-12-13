package ru.eltech.dto;

public record WorkerInRoomDto(
        Long id,
        String login,
        String shift
) {}
