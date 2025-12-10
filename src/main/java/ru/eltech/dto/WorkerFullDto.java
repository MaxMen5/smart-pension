package ru.eltech.dto;

import ru.eltech.entity.Worker;
import ru.eltech.entity.Room;
import ru.eltech.entity.WorkerSchedule;

import java.time.LocalDate;
import java.util.Set;
import java.util.List;
import java.util.stream.Collectors;

public record WorkerFullDto(
        Long id,
        String login,
        String shift,
        Set<String> roomNumbers,
        List<LocalDate> workDates
) {
    // Статический фабричный метод для создания из Entity
    public static WorkerFullDto fromEntity(Worker worker) {
        Set<String> roomNumbers = worker.getRooms().stream()
                .map(Room::getRoomNumber)
                .collect(Collectors.toSet());

        List<LocalDate> workDates = worker.getSchedules().stream()
                .map(WorkerSchedule::getWorkDate)
                .sorted()
                .collect(Collectors.toList());

        return new WorkerFullDto(
                worker.getId(),
                worker.getLogin(),
                worker.getShift(),
                roomNumbers,
                workDates
        );
    }
}