package ru.eltech.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.eltech.dto.WorkerDto;
import ru.eltech.services.WorkerService;

import java.util.List;

@RestController
@RequestMapping("/api/workers")
public class WorkerController {

    private final WorkerService workerService;
    WorkerController(WorkerService workerService) { this.workerService = workerService; }

    @GetMapping("/find_all")
    public List<WorkerDto> findAll() {
        return workerService.getAllWorkers();
    }

    @GetMapping("/get_worker")
    public WorkerDto getWorker(@RequestParam String login) {
        return workerService.getWorker(login);
    }

    @PutMapping("/update")
    public ResponseEntity<Void> update(@RequestBody WorkerDto workerDto) {
        workerService.updateWorker(workerDto);
        return ResponseEntity.ok().build();
    }
}