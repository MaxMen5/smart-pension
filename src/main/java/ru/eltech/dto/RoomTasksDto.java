package ru.eltech.dto;

import java.util.List;

public record RoomTasksDto(
        Long roomId,
        String roomNumber,
        String roomType,
        Integer freeSpots,
        List<WorkerInRoomDto> workers,
        List<TaskStatusDto> tasks,
        Integer completedTasks,
        Integer totalTasks
) {}

