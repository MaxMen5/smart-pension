package ru.eltech.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.eltech.entity.Resident;

@Repository
public interface ResidentRepository extends JpaRepository<Resident, Long> {

    Long countByRoomId(Long roomId);

}
