package ru.eltech.dto;

import java.time.LocalDate;
import java.util.Set;
import java.util.List;

public record WorkerUpdateDto(
        Long id,
        String login,
        String shift,
        Set<String> roomNumbers,
        List<LocalDate> workDates
) {
        public WorkerUpdateDto {
                // Гарантируем, что коллекции не null
                roomNumbers = roomNumbers != null ? roomNumbers : Set.of();
                workDates = workDates != null ? workDates : List.of();
        }
}