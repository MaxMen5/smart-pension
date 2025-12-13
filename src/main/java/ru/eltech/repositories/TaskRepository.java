package ru.eltech.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.eltech.entity.Task;
import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByTaskDate(LocalDate date);

    @Query("SELECT COUNT(t) FROM Task t WHERE t.taskDate = :date")
    long countByTaskDate(@Param("date") LocalDate date);
}