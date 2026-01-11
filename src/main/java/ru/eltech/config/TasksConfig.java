package ru.eltech.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@Data
@ConfigurationProperties(prefix = "tasks")
public class TasksConfig {
    private int daysAhead = 7;
    private List<DailyTask> daily;

    @Data
    public static class DailyTask {
        private String title;
        private String description;
    }
}