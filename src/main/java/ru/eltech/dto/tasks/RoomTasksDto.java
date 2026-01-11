package ru.eltech.dto.tasks;

import ru.eltech.dto.workers.WorkerInRoomDto;

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

