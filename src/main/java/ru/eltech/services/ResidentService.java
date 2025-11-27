package ru.eltech.services;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.eltech.dto.CreateResidentRequest;
import ru.eltech.dto.ResidentDto;
import ru.eltech.entity.Archive;
import ru.eltech.entity.Resident;
import ru.eltech.repositories.ArchiveRepository;
import ru.eltech.repositories.ResidentRepository;
import ru.eltech.repositories.RoomRepository;
import ru.eltech.entity.Room;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ResidentService {

    private final ResidentRepository residentRepository;
    private final RoomRepository roomRepository;
    private final ArchiveRepository archiveRepository;
    private final RoomService roomService;


    public ResidentService(ResidentRepository residentRepository, RoomRepository roomRepository, ArchiveRepository archiveRepository, RoomService roomService) {
        this.residentRepository = residentRepository;
        this.roomRepository = roomRepository;
        this.archiveRepository = archiveRepository;
        this.roomService = roomService;
    }

    public List<ResidentDto> getAllResidents() {
        List<Resident> residents = residentRepository.findAll();

        Map<Long, String> roomNumbers = roomRepository.findAll().stream()
                .collect(Collectors.toMap(Room::getId, Room::getRoomNumber));

        return residents.stream()
                .map(resident -> new ResidentDto(
                        resident.getIdResident(),
                        resident.getLastName(),
                        resident.getFirstName(),
                        resident.getMiddleName(),
                        resident.getBirthDate(),
                        resident.getGender(),
                        resident.getPassportNumber(),
                        resident.getPhone(),
                        resident.getEmail(),
                        resident.getAdmissionDate(),
                        roomNumbers.get(resident.getRoomId())
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public Resident createResident(CreateResidentRequest request) {
        Room room = roomRepository.findByRoomNumber(request.roomNumber())
                .orElseThrow(() -> new RuntimeException("Комната с номером " + request.roomNumber() + " не найдена"));

        Resident resident = new Resident();
        resident.setLastName(request.lastName());
        resident.setFirstName(request.firstName());
        resident.setMiddleName(request.middleName());
        resident.setBirthDate(request.birthDate());
        resident.setGender(request.gender());
        resident.setPassportNumber(request.passportNumber());
        resident.setPhone(request.phone());
        resident.setEmail(request.email());
        resident.setAdmissionDate(request.admissionDate());
        resident.setRoomId(room.getId());

        Resident savedResident = residentRepository.save(resident);
        roomService.decrementFreeSpots(room.getId());
        return savedResident;
    }
}
