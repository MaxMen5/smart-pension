package ru.eltech.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.eltech.dto.WorkerDto;
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

    // Получить всех работников с комнатами и графиком
    public List<WorkerDto> getAllWorkers() {
        List<Worker> workers = workerRepository.findAllWithRoomsAndSchedules();

        return workers.stream()
                .map(this::fromEntity)
                .collect(Collectors.toList());
    }

    // Получить одного работника с комнатами и графиком
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

    // ОБНОВИТЬ ВСЕ: комнаты и график
    @Transactional
    public void updateWorker(WorkerDto dto) {
        // 1. Находим работника
        Worker worker = workerRepository.findById(dto.id())
                .orElseThrow(() -> new RuntimeException("Сотрудник не найден с id: " + dto.id()));

        // Обновляем смену
        worker.setShift(dto.shift());

        // 2. Обновляем комнаты
        updateWorkerRooms(worker, dto.roomNumbers());

        // 3. Обновляем график - ИСПРАВЛЕННЫЙ МЕТОД
        updateWorkerScheduleFixed(worker, dto.workDates());
    }

    // Обновление комнат
    private void updateWorkerRooms(Worker worker, Set<String> roomNumbers) {
        // Очищаем текущие комнаты
        worker.getRooms().clear();

        if (roomNumbers != null && !roomNumbers.isEmpty()) {
            // Находим Room entities по номерам
            Set<Room> rooms = roomNumbers.stream()
                    .map(roomNumber -> roomRepository.findByRoomNumber(roomNumber)
                            .orElseThrow(() -> new RuntimeException("Комната не найдена: " + roomNumber)))
                    .collect(Collectors.toSet());

            // Устанавливаем новые комнаты
            worker.getRooms().addAll(rooms);
        }
    }

    // ИСПРАВЛЕННЫЙ МЕТОД: Обновление графика
    @Transactional
    public void updateWorkerScheduleFixed(Worker worker, List<LocalDate> workDates) {
        System.out.println("Обновляем график для работника ID: " + worker.getId());

        // 1. Удаляем старый график через репозиторий
        workerScheduleRepository.deleteByWorkerId(worker.getId());

        // 2. ОЧИЩАЕМ коллекцию в сессии Hibernate
        worker.getSchedules().clear();

        // 3. Принудительно сбрасываем изменения в БД
        workerRepository.flush();

        // 4. Создаем новые записи графика
        if (workDates != null && !workDates.isEmpty()) {
            System.out.println("Добавляем " + workDates.size() + " дат в график");

            for (LocalDate date : workDates) {
                WorkerSchedule schedule = new WorkerSchedule();
                schedule.setWorker(worker);
                schedule.setWorkDate(date);

                // Сохраняем через репозиторий, не через каскад
                workerScheduleRepository.save(schedule);
            }
        }

        System.out.println("График успешно обновлен");
    }
}