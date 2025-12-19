package ru.eltech.dto;

import java.time.LocalDate;

public record ArchiveResidentDto(
    Long residentId,
    LocalDate archiveDate,
    String archiveReason
) {}
