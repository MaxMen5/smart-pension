package ru.eltech.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.eltech.dto.rooms.RoomDto;
import ru.eltech.entity.Room;
import ru.eltech.services.RoomService;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;
    RoomController(RoomService roomService) { this.roomService = roomService; }

    @GetMapping("/find_free_rooms")
    public List<String> findFreeRooms(@RequestParam String gender) {
        return roomService.getFreeSpotsByGender(gender);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteRoom(@RequestParam Long roomId) {
        roomService.deleteRoom(roomId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/find_all")
    public List<Room> findAll() {
        return roomService.findAll();
    }

    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody RoomDto roomDto) {
        roomService.createRoom(roomDto);
        return ResponseEntity.noContent().build();
    }
}
