package ru.eltech.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.eltech.dto.ResidentDto;
import ru.eltech.dto.ResidentSmallDto;
import ru.eltech.entity.Resident;

import java.util.List;

@Repository
public interface ResidentRepository extends JpaRepository<Resident, Long> {

    Long countByRoomId(Long roomId);

    @Query("SELECT r.idResident, r.lastName, r.firstName, r.middleName, " +
            "r.birthDate, r.gender, r.passportNumber, r.phone, r.email, " +
            "r.admissionDate, room.roomNumber " +
            "FROM Resident r " +
            "LEFT JOIN Room room ON r.roomId = room.id")
    List<ResidentDto> findAllWithRoomNumber();

    @Query("SELECT r.idResident as idResident, r.lastName as lastName, " +
            "r.firstName as firstName, r.middleName as middleName, " +
            "r.gender as gender, room.roomNumber as roomNumber " +
            "FROM Resident r " +
            "LEFT JOIN Room room ON r.roomId = room.id")
    List<ResidentSmallDto> findAllSmallDto();

}
