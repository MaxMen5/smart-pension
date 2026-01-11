package ru.eltech.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.eltech.dto.workers.WorkerDto;
import ru.eltech.entity.Worker;
import ru.eltech.entity.WorkerSchedule;
import ru.eltech.entity.Room;
import ru.eltech.repositories.WorkerRepository;
import ru.eltech.repositories.RoomRepository;
import ru.eltech.repositories.WorkerScheduleRepository;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class WorkerService {

    private final WorkerRepository workerRepository;
    private final RoomRepository roomRepository;
    private final WorkerScheduleRepository workerScheduleRepository;

    public WorkerService(
            WorkerRepository workerRepository,
            RoomRepository roomRepository,
            WorkerScheduleRepository workerScheduleRepository) {
        this.workerRepository = workerRepository;
        this.roomRepository = roomRepository;
        this.workerScheduleRepository = workerScheduleRepository;
    }

    public List<WorkerDto> getAllWorkers() {
        List<Worker> workers = workerRepository.findAllWithRoomsAndSchedules();

        return workers.stream()
                .map(this::fromEntity)
                .collect(Collectors.toList());
    }

    public WorkerDto getWorker(String login) {
        Worker worker = workerRepository.findByLoginWithRoomsAndSchedules(login)
                .orElseThrow(() -> new RuntimeException("Сотрудник не найден: " + login));

        return fromEntity(worker);
    }

    private WorkerDto fromEntity(Worker worker) {
        Set<String> roomNumbers = worker.getRooms().stream()
                .map(Room::getRoomNumber)
                .sorted()
                .collect(Collectors.toCollection(LinkedHashSet::new));

        List<LocalDate> workDates = worker.getSchedules().stream()
                .map(WorkerSchedule::getWorkDate)
                .sorted()
                .collect(Collectors.toList());

        return new WorkerDto(
                worker.getId(),
                worker.getLogin(),
                worker.getShift(),
                roomNumbers,
                workDates
        );
    }

    @Transactional
    public void updateWorker(WorkerDto dto) {
        Worker worker = workerRepository.findById(dto.id())
                .orElseThrow(() -> new RuntimeException("Сотрудник не найден с id: " + dto.id()));
        worker.setShift(dto.shift());
        updateWorkerRooms(worker, dto.roomNumbers());
        updateWorkerScheduleFixed(worker, dto.workDates());
    }

    private void updateWorkerRooms(Worker worker, Set<String> roomNumbers) {
        worker.getRooms().clear();
        if (roomNumbers != null && !roomNumbers.isEmpty()) {
            Set<Room> rooms = roomNumbers.stream()
                    .map(roomNumber -> roomRepository.findByRoomNumber(roomNumber)
                            .orElseThrow(() -> new RuntimeException("Комната не найдена: " + roomNumber)))
                    .collect(Collectors.toSet());
            worker.getRooms().addAll(rooms);
        }
    }

    @Transactional
    public void updateWorkerScheduleFixed(Worker worker, List<LocalDate> workDates) {
        workerScheduleRepository.deleteByWorkerId(worker.getId());
        worker.getSchedules().clear();
        workerRepository.flush();
        if (workDates != null && !workDates.isEmpty()) {
            System.out.println("Добавляем " + workDates.size() + " дат в график");

            for (LocalDate date : workDates) {
                WorkerSchedule schedule = new WorkerSchedule();
                schedule.setWorker(worker);
                schedule.setWorkDate(date);
                workerScheduleRepository.save(schedule);
            }
        }

        System.out.println("График успешно обновлен");
    }
}