package ru.eltech.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.eltech.dto.CreateTaskDto;
import ru.eltech.dto.DailyRoomTasksDto;
import ru.eltech.dto.TaskStatusDto;
import ru.eltech.services.TaskService;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/find_all_tasks")
    public ResponseEntity<DailyRoomTasksDto> getDailyTasks(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        if (date == null) {
            date = LocalDate.now();
        }
        DailyRoomTasksDto dailyTasks = taskService.getDailyTasks(date);
        return ResponseEntity.ok(dailyTasks);
    }

    @DeleteMapping("/delete/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/create")
    public ResponseEntity<TaskStatusDto> createTask(@RequestBody CreateTaskDto request) {
        TaskStatusDto createdTask = taskService.createTask(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }
}