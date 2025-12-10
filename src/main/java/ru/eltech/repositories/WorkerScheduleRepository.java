package ru.eltech.repositories;

import ru.eltech.entity.WorkerSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkerScheduleRepository extends JpaRepository<WorkerSchedule, Long> {

    @Modifying
    @Query("DELETE FROM WorkerSchedule ws WHERE ws.worker.id = :workerId")
    void deleteByWorkerId(@Param("workerId") Long workerId);

}