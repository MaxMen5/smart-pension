package ru.eltech.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.eltech.dto.ArchiveResidentDto;
import ru.eltech.entity.Archive;
import ru.eltech.services.ArchiveService;

import java.util.List;

@RestController
@RequestMapping("/api/archive")
public class ArchiveController {

    private final ArchiveService archiveService;
    public ArchiveController(ArchiveService archiveService) { this.archiveService = archiveService; }

    @GetMapping("/find_all")
    public List<Archive> findAll() {
        return archiveService.getAllArchive();
    }

    @PostMapping("/remove_resident")
    public ResponseEntity<Void> archive(@RequestBody ArchiveResidentDto archiveResidentDto) {
        archiveService.archive(archiveResidentDto);
        return ResponseEntity.ok().build();
    }
}
