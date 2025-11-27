package ru.eltech.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.eltech.entity.Room;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("SELECT r.roomNumber FROM Room r WHERE r.roomType = :roomType AND r.freeSpots > 0")
    List<String> findFreeRoom(@Param("roomType") String roomType);

    Optional<Room> findByRoomNumber(String roomNumber);
}
