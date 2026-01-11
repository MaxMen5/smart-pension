package ru.eltech.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.eltech.dto.residents.CreateResidentDto;
import ru.eltech.dto.residents.ResidentDto;
import ru.eltech.dto.residents.ResidentSmallDto;
import ru.eltech.services.ResidentService;

import java.util.List;

@RestController
@RequestMapping("/api/residents")
public class ResidentController {

    private final ResidentService residentService;
    ResidentController(ResidentService residentService) { this.residentService = residentService; }

    @GetMapping("/find_all")
    public List<ResidentDto> findAll() {
        return residentService.getAllResidents();
    }

    @GetMapping("/find_all_small")
    public List<ResidentSmallDto> findAllSmall() {
        return residentService.getResidentSmall();
    }

    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody CreateResidentDto resident) {
        residentService.createResident(resident);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update")
    public ResponseEntity<Void> update(@RequestBody ResidentDto resident) {
        residentService.updateResident(resident);
        return ResponseEntity.ok().build();
    }
}
