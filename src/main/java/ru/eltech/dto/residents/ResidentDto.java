package ru.eltech.dto.residents;

import java.time.LocalDate;

public record ResidentDto(
        Long idResident,
        String lastName,
        String firstName,
        String middleName,
        LocalDate birthDate,
        String gender,
        String passportNumber,
        String phone,
        String email,
        LocalDate admissionDate,
        String roomNumber
) {}