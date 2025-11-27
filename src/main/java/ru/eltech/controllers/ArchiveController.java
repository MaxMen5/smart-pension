package ru.eltech.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.eltech.dto.ArchiveResidentRequest;
import ru.eltech.entity.Archive;
import ru.eltech.services.ArchiveService;

import java.util.List;

@RestController
@RequestMapping("/api/archive")
public class ArchiveController {

    @Autowired
    private ArchiveService archiveService;

    @GetMapping("/find_all")
    public List<Archive> findAll() {
        return archiveService.getAllArchive();
    }

    @PostMapping("/archive")
    public ResponseEntity<Void> archive(@RequestBody ArchiveResidentRequest archive) {
        archiveService.archive(archive);
        return ResponseEntity.ok().build();
    }
}
