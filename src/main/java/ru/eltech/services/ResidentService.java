package ru.eltech.services;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.eltech.dto.CreateResidentDto;
import ru.eltech.dto.ResidentDto;
import ru.eltech.dto.ResidentSmallDto;
import ru.eltech.entity.Resident;
import ru.eltech.exception.MyException;
import ru.eltech.repositories.ResidentRepository;
import ru.eltech.repositories.RoomRepository;
import ru.eltech.entity.Room;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResidentService {

    private final ResidentRepository residentRepository;
    private final RoomRepository roomRepository;

    public ResidentService(ResidentRepository residentRepository, RoomRepository roomRepository) {
        this.residentRepository = residentRepository;
        this.roomRepository = roomRepository;
    }

    @Transactional
    public void createResident(CreateResidentDto request) {
        Room room = roomRepository.findByRoomNumber(request.roomNumber())
                .orElseThrow(() -> new MyException("Комната не найдена"));

        Resident resident = mapToEntity(request, room);
        residentRepository.save(resident);
        room.decrementFreeSpots();
        roomRepository.save(room);
    }

    @Transactional
    public void updateResident(ResidentDto residentDto) {
        Resident resident = residentRepository.findById(residentDto.idResident())
                .orElseThrow(() -> new MyException("Постоялец не найден"));

        Room newRoom = roomRepository.findByRoomNumber(residentDto.roomNumber())
                .orElseThrow(() -> new MyException("Новая комната не найдена"));

        Room oldRoom = roomRepository.findById(resident.getRoomId())
                .orElseThrow(() -> new MyException("Старая комната не найдена"));

        if (!oldRoom.equals(newRoom)) {
            oldRoom.incrementFreeSpots();
            roomRepository.save(oldRoom);
            resident.setRoomId(newRoom.getId());
            newRoom.decrementFreeSpots();
            roomRepository.save(newRoom);
        }

        updateEntityFromDto(resident, residentDto);
        residentRepository.save(resident);
    }

    private Resident mapToEntity(CreateResidentDto dto, Room room) {
        Resident resident = new Resident();
        resident.setLastName(dto.lastName());
        resident.setFirstName(dto.firstName());
        resident.setMiddleName(dto.middleName());
        resident.setBirthDate(dto.birthDate());
        resident.setGender(dto.gender());
        resident.setPassportNumber(dto.passportNumber());
        resident.setPhone(dto.phone());
        resident.setEmail(dto.email());
        resident.setAdmissionDate(dto.admissionDate());
        resident.setRoomId(room.getId());
        return resident;
    }

    public List<ResidentSmallDto> getResidentSmall() {
        return residentRepository.findAllSmallDto().stream()
                .map(obj -> new ResidentSmallDto(
                        (Long) obj[0],
                        (String) obj[1],
                        (String) obj[2],
                        (String) obj[3],
                        (String) obj[4],
                        (String) obj[5]
                ))
                .sorted(Comparator.comparing(ResidentSmallDto::roomNumber))
                .collect(Collectors.toList());
    }

    public List<ResidentDto> getAllResidents() {
        return residentRepository.findAllWithRoomNumber().stream()
                .map(obj -> new ResidentDto(
                        (Long) obj[0],
                        (String) obj[1],
                        (String) obj[2],
                        (String) obj[3],
                        (LocalDate) obj[4],
                        (String) obj[5],
                        (String) obj[6],
                        (String) obj[7],
                        (String) obj[8],
                        (LocalDate) obj[9],
                        (String) obj[10]
                ))
                .collect(Collectors.toList());
    }

    private void updateEntityFromDto(Resident resident, ResidentDto dto) {

        resident.setLastName(dto.lastName());
        resident.setFirstName(dto.firstName());
        resident.setMiddleName(dto.middleName());
        resident.setBirthDate(dto.birthDate());
        resident.setGender(dto.gender());
        resident.setPassportNumber(dto.passportNumber());
        resident.setPhone(dto.phone());
        resident.setEmail(dto.email());
        resident.setAdmissionDate(dto.admissionDate());
    }
}
