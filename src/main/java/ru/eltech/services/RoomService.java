package ru.eltech.services;

import org.springframework.stereotype.Service;
import ru.eltech.dto.RoomDto;
import ru.eltech.entity.Room;
import ru.eltech.exception.MyException;
import ru.eltech.repositories.ResidentRepository;
import ru.eltech.repositories.RoomRepository;

import java.util.List;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final ResidentRepository residentRepository;

    public RoomService(RoomRepository roomRepository, ResidentRepository residentRepository) {
        this.roomRepository = roomRepository;
        this.residentRepository = residentRepository;
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

    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    public void deleteRoom(Long roomId) {
        if (residentRepository.countByRoomId(roomId) == 0) roomRepository.deleteById(roomId);
        else throw new MyException("Нельзя удалить комнату, в которой живут постояльцы!");
    }

    public void createRoom(RoomDto roomDto) {

        if (roomRepository.existsByRoomNumber(roomDto.roomNumber())) {
            throw new MyException("Комната с номером " + roomDto.roomNumber() + " уже существует");
        }

        Room room = new Room();
        room.setRoomNumber(roomDto.roomNumber());
        String male = roomDto.roomType().equals("Мужской") ? "М" : "Ж";
        room.setRoomType(male);
        room.setFreeSpots(roomDto.freeSpots());

        roomRepository.save(room);
    }
}
