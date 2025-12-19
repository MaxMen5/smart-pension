package ru.eltech.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.eltech.entity.Archive;

@Repository
public interface ArchiveRepository extends JpaRepository<Archive, Long> {

}
