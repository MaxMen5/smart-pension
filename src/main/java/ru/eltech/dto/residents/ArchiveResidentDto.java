package ru.eltech.dto.residents;

import java.time.LocalDate;

public record ArchiveResidentDto(
    Long residentId,
    LocalDate archiveDate,
    String archiveReason
) {}
