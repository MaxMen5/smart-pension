package ru.eltech.services;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.eltech.dto.ArchiveResidentDto;
import ru.eltech.entity.Archive;
import ru.eltech.entity.Resident;
import ru.eltech.entity.Room;
import ru.eltech.exception.MyException;
import ru.eltech.repositories.ArchiveRepository;
import ru.eltech.repositories.ResidentRepository;
import ru.eltech.repositories.RoomRepository;

import java.util.List;

@Service
public class ArchiveService {

    private final ArchiveRepository archiveRepository;
    private final ResidentRepository residentRepository;
    private final RoomRepository roomRepository;
    private final RoomService roomService;

    public ArchiveService(ArchiveRepository archiveRepository, ResidentRepository residentRepository, RoomRepository roomRepository, RoomService roomService) {
        this.archiveRepository = archiveRepository;
        this.residentRepository = residentRepository;
        this.roomRepository = roomRepository;
        this.roomService = roomService;
    }

    public List<Archive> getAllArchive() {
        return archiveRepository.findAll();
    }

    @Transactional
    public void archive(ArchiveResidentDto archive) {

        Resident resident = residentRepository.findById(archive.residentId())
                .orElseThrow(() -> new MyException("Постоялец не найден"));

        Archive archiveEntity = new Archive();
        archiveEntity.setLastName(resident.getLastName());
        archiveEntity.setFirstName(resident.getFirstName());
        archiveEntity.setMiddleName(resident.getMiddleName());
        archiveEntity.setBirthDate(resident.getBirthDate());
        archiveEntity.setGender(resident.getGender());
        archiveEntity.setPassportNumber(resident.getPassportNumber());
        archiveEntity.setPhone(resident.getPhone());
        archiveEntity.setEmail(resident.getEmail());
        archiveEntity.setAdmissionDate(resident.getAdmissionDate());

        String roomNumber = roomRepository.findById(resident.getRoomId())
                .map(Room::getRoomNumber)
                .orElse("Unknown");

        archiveEntity.setRoomNumber(roomNumber);
        archiveEntity.setArchiveDate(archive.archiveDate());
        archiveEntity.setArchiveReason(archive.archiveReason());

        archiveRepository.save(archiveEntity);
        residentRepository.delete(resident);
        roomService.incrementFreeSpots(resident.getRoomId());
    }
}
