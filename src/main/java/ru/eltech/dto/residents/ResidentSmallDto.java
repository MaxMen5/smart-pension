package ru.eltech.dto.residents;

public record ResidentSmallDto(
        Long idResident,
        String lastName,
        String firstName,
        String middleName,
        String gender,
        String roomNumber
) {}