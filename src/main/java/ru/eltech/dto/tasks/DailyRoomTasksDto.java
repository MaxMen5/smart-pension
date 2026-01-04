package ru.eltech.dto.tasks;

import java.time.LocalDate;
import java.util.List;

public record DailyRoomTasksDto(
        LocalDate date,
        List<RoomTasksDto> rooms
) {}

