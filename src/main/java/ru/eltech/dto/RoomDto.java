package ru.eltech.dto;

public record RoomDto (
     String roomNumber,
     String roomType,
     Integer freeSpots
)
{}
