package ru.eltech.services;

import org.springframework.stereotype.Service;
import ru.eltech.entity.Room;
import ru.eltech.repositories.RoomRepository;

import java.util.List;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public void decrementFreeSpots(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Комната не найдена"));
        if (room.getFreeSpots() <= 0) {
            throw new RuntimeException("В комнате нет свободных мест");
        }
        room.setFreeSpots(room.getFreeSpots() - 1);
        roomRepository.save(room);
    }

    public void incrementFreeSpots(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Комната не найдена"));
        if (room.getFreeSpots() > 2) {
            throw new RuntimeException("Комната свободна");
        }
        room.setFreeSpots(room.getFreeSpots() + 1);
        roomRepository.save(room);
    }

    public List<String> getFreeSpotsByGender(String gender) {
        return roomRepository.findFreeRoom(gender);
    }
}
