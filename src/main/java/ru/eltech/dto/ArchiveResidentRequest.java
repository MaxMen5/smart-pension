package ru.eltech.dto;

import java.time.LocalDate;

public record ArchiveResidentRequest (
    Long residentId,
    LocalDate archiveDate,
    String archiveReason
) {}
