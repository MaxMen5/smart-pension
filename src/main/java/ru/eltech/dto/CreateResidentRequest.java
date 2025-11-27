package ru.eltech.dto;

import java.time.LocalDate;

public record CreateResidentRequest(
        String lastName,
        String firstName,
        String middleName,
        LocalDate birthDate,
        String gender,
        String passportNumber,
        String phone,
        String email,
        LocalDate admissionDate,
        String roomNumber  // принимаем номер комнаты вместо ID
) {}
