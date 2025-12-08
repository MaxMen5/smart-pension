package ru.eltech.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.eltech.dto.RoomDto;
import ru.eltech.entity.Room;
import ru.eltech.services.RoomService;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping("/find_free_rooms")
    public List<String> findFreeRooms(@RequestParam String gender) {
        return roomService.getFreeSpotsByGender(gender);
    }

    @DeleteMapping("/delete")
    public void deleteRoom(@RequestParam Long roomId) {
        roomService.deleteRoom(roomId);
    }

    @GetMapping("/find_all")
    public List<Room> findAll() {
        return roomService.findAll();
    }

    @PostMapping("/create")
    public void create(@RequestBody RoomDto roomDto) {
        roomService.createRoom(roomDto);
    }
}
