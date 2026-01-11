package ru.eltech.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.eltech.dto.tasks.CompleteTaskDto;
import ru.eltech.dto.tasks.CreateTaskDto;
import ru.eltech.dto.tasks.DailyRoomTasksDto;
import ru.eltech.dto.tasks.TaskStatusDto;
import ru.eltech.services.TaskService;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/generate")
    public ResponseEntity<Void> generateTasks() {
        taskService.generateTasks();
        return ResponseEntity.ok().build();
    }

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

    @GetMapping("/worker/find_all_tasks")
    public ResponseEntity<DailyRoomTasksDto> getWorkerTasks(@RequestParam String login) {
        DailyRoomTasksDto workerTasks = taskService.getWorkerTasks(login);
        return ResponseEntity.ok(workerTasks);
    }

    @PutMapping("/complete/{taskId}")
    public ResponseEntity<TaskStatusDto> completeTask(
            @PathVariable Long taskId,
            @RequestBody CompleteTaskDto request) {
        TaskStatusDto updatedTask = taskService.toggleTaskCompletion(taskId, request);
        return ResponseEntity.ok(updatedTask);
    }
}