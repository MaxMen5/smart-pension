package ru.eltech.repositories;

import ru.eltech.entity.Worker;
import ru.eltech.entity.WorkerSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WorkerScheduleRepository extends JpaRepository<WorkerSchedule, Long> {

    @Modifying
    @Query("DELETE FROM WorkerSchedule ws WHERE ws.worker.id = :workerId")
    void deleteByWorkerId(@Param("workerId") Long workerId);

    // Проверить, работает ли сотрудник в определенную дату
    boolean existsByWorkerAndWorkDate(Worker worker, LocalDate date);

    // Найти всех сотрудников, работающих в определенную дату
    @Query("SELECT ws.worker FROM WorkerSchedule ws WHERE ws.workDate = :date")
    List<Worker> findWorkersByWorkDate(@Param("date") LocalDate date);

    boolean existsByWorkerIdAndWorkDate(Long workerId, LocalDate workDate);

}