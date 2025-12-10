package ru.eltech.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.eltech.dto.WorkerFullDto;
import ru.eltech.dto.WorkerUpdateDto;
import ru.eltech.services.WorkerService;

import java.util.List;

@RestController
@RequestMapping("/api/workers")
public class WorkerController {

    @Autowired
    private WorkerService workerService;

    @GetMapping("/find_all")
    public List<WorkerFullDto> findAll() {
        return workerService.getAllWorkers();
    }

    @GetMapping("/get")
    public WorkerFullDto get(@RequestParam String login) {
        return workerService.getWorker(login);
    }

    @PutMapping("/update")
    public void update(@RequestBody WorkerUpdateDto dto) {
        workerService.updateWorker(dto);
    }

    @PutMapping("/{id}/update")
    public void updateById(@PathVariable Long id, @RequestBody WorkerUpdateDto dto) {
        WorkerUpdateDto updatedDto = new WorkerUpdateDto(
                id,
                dto.login(),
                dto.shift(),
                dto.roomNumbers(),
                dto.workDates()
        );
        workerService.updateWorker(updatedDto);
    }
}