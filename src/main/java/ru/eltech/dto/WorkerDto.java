package ru.eltech.dto;

import java.time.LocalDate;
import java.util.Set;
import java.util.List;

public record WorkerDto(
        Long id,
        String login,
        String shift,
        Set<String> roomNumbers,
        List<LocalDate> workDates
) {}