package ru.eltech.init;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.eltech.services.TaskGeneratorService;

@Component
@RequiredArgsConstructor
public class TaskInitializer {

    private final TaskGeneratorService taskGeneratorService;

    @EventListener(ApplicationReadyEvent.class)
    public void onStartup() {
        System.out.println("=== ЗАПУСК СЕРВЕРА ===");
        taskGeneratorService.ensureTasksOnStartup();
        System.out.println("=== ЗАДАЧИ ГАРАНТИРОВАНЫ ===");
    }
}