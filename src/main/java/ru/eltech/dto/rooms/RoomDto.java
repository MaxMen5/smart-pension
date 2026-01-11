package ru.eltech.dto.rooms;

public record RoomDto (
     String roomNumber,
     String roomType,
     Integer freeSpots
)
{}
