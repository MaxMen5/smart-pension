package ru.eltech.dto;

import java.time.LocalDate;

public record CreateTaskDto(
        String title,
        String description,
        LocalDate taskDate,
        Long roomId
) {}