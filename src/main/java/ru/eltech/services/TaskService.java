package ru.eltech.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.eltech.dto.*;
import ru.eltech.entity.*;
import ru.eltech.exception.MyException;
import ru.eltech.repositories.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final RoomRepository roomRepository;
    private final WorkerScheduleRepository workerScheduleRepository;
    private final WorkerRepository workerRepository;

    @Transactional(readOnly = true)
    public DailyRoomTasksDto getDailyTasks(LocalDate date) {
        // Получаем все комнаты
        List<Room> allRooms = roomRepository.findAllByOrderByRoomNumberAsc();

        // Создаем мапу для быстрого доступа к задачам по комнатам
        Map<Long, List<Task>> tasksByRoom = taskRepository.findByTaskDate(date).stream()
                .collect(Collectors.groupingBy(task -> task.getRoom().getId()));

        // Получаем всех сотрудников, работающих в этот день
        List<Worker> workingWorkers = workerScheduleRepository.findWorkersByWorkDate(date);
        Map<Long, Worker> workingWorkersMap = workingWorkers.stream()
                .collect(Collectors.toMap(Worker::getId, w -> w));

        // Для каждой комнаты собираем данные
        List<RoomTasksDto> roomDTOs = allRooms.stream()
                .map(room -> convertRoomToDTO(room, date, tasksByRoom.get(room.getId()), workingWorkersMap))
                .collect(Collectors.toList());

        return new DailyRoomTasksDto(date, roomDTOs);
    }

    private RoomTasksDto convertRoomToDTO(Room room, LocalDate date,
                                          List<Task> tasks, Map<Long, Worker> workingWorkersMap) {
        // Получаем задачи комнаты
        List<TaskStatusDto> taskDTOs;
        int totalTasks;
        int completedTasks;

        if (tasks != null) {
            taskDTOs = tasks.stream()
                    .map(this::convertTaskToDTO)
                    .collect(Collectors.toList());
            totalTasks = tasks.size();
            completedTasks = (int) tasks.stream()
                    .filter(Task::getIsCompleted)
                    .count();
        } else {
            taskDTOs = Collections.emptyList();
            totalTasks = 0;
            completedTasks = 0;
        }

        // Получаем сотрудников, закрепленных за комнатой И работающих в этот день
        List<WorkerInRoomDto> workersInRoom = room.getWorkers().stream()
                .filter(worker -> workingWorkersMap.containsKey(worker.getId()))
                .map(this::convertWorkerToDTO)
                .collect(Collectors.toList());

        return new RoomTasksDto(
                room.getId(),
                room.getRoomNumber(),
                room.getRoomType(),
                room.getFreeSpots(),
                workersInRoom,
                taskDTOs,
                completedTasks,
                totalTasks
        );
    }

    private TaskStatusDto convertTaskToDTO(Task task) {
        String completedByWorkerLogin = null;

        // Если есть исполнитель, получаем его логин
        if (task.getCompletedByWorkerId() != null) {
            completedByWorkerLogin = workerRepository.findById(task.getCompletedByWorkerId())
                    .map(Worker::getLogin)
                    .orElse(null);
        }

        return new TaskStatusDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getIsCompleted(),
                task.getCompletedByWorkerId(),
                completedByWorkerLogin
        );
    }

    private WorkerInRoomDto convertWorkerToDTO(Worker worker) {
        return new WorkerInRoomDto(
                worker.getId(),
                worker.getLogin(),
                worker.getShift()
        );
    }





    @Transactional
    public void deleteTask(Long taskId) {

        // Находим задачу
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new MyException("Задача не найдена"));

        // Проверяем дату задачи
        LocalDate today = LocalDate.now();
        LocalDate maxAllowedDate = today.plusDays(7);

        if (task.getTaskDate().isBefore(today)) {
            throw new RuntimeException("Нельзя удалять задачи за прошедшие даты");
        }

        if (task.getTaskDate().isAfter(maxAllowedDate)) {
            throw new RuntimeException("Нельзя удалять задачи более чем на неделю вперед");
        }

        // Удаляем
        taskRepository.delete(task);
    }

    @Transactional
    public TaskStatusDto createTask(CreateTaskDto request) {
        // Валидация даты
        LocalDate taskDate = request.taskDate() != null ? request.taskDate() : LocalDate.now();
        LocalDate today = LocalDate.now();
        LocalDate maxAllowedDate = today.plusDays(7);

        if (taskDate.isBefore(today)) {
            throw new MyException("Нельзя создавать задачи за прошедшие даты");
        }

        if (taskDate.isAfter(maxAllowedDate)) {
            throw new MyException("Нельзя создавать задачи более чем на неделю вперед");
        }

        // Проверяем комнату
        Room room = roomRepository.findById(request.roomId())
                .orElseThrow(() -> new MyException("Комната не найдена"));

        // Создаем задачу
        Task task = new Task();
        task.setTitle(request.title());
        task.setDescription(request.description());
        task.setTaskDate(taskDate);
        task.setRoom(room);
        task.setIsCompleted(false);
        task.setIsAutoGenerated(false); // Ручное создание

        // Сохраняем
        Task savedTask = taskRepository.save(task);

        // Возвращаем DTO
        return convertTaskToDTO(savedTask);
    }
}