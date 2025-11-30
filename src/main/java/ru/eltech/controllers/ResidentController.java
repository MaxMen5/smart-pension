package ru.eltech.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.eltech.dto.CreateResidentRequest;
import ru.eltech.dto.ResidentDto;
import ru.eltech.entity.Resident;
import ru.eltech.services.ResidentService;

import java.util.List;

@RestController
@RequestMapping("/api/residents")
public class ResidentController {

    @Autowired
    private ResidentService residentService;

    @GetMapping("/find_all")
    public List<ResidentDto> findAll() {
        return residentService.getAllResidents();
    }

    @PostMapping("/create")
    public ResponseEntity<Resident> create(@RequestBody CreateResidentRequest resident) {
        Resident savedResident = residentService.createResident(resident);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedResident);
    }

    @PutMapping("/update")
    public void update(@RequestBody ResidentDto resident) {
        residentService.updateResident(resident);
    }
}
