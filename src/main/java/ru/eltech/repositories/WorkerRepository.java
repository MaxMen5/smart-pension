package ru.eltech.repositories;

import ru.eltech.entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, Long> {

    Optional<Worker> findByLogin(String login);

    @Query("SELECT DISTINCT w FROM Worker w " +
            "LEFT JOIN FETCH w.rooms " +
            "LEFT JOIN FETCH w.schedules " +
            "ORDER BY w.login")
    List<Worker> findAllWithRoomsAndSchedules();

    @Query("SELECT DISTINCT w FROM Worker w " +
            "LEFT JOIN FETCH w.rooms " +
            "LEFT JOIN FETCH w.schedules " +
            "WHERE w.login = :login")
    Optional<Worker> findByLoginWithRoomsAndSchedules(@Param("login") String login);
}