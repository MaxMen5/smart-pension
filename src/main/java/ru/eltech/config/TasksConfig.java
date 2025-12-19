package ru.eltech.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@Data
@ConfigurationProperties(prefix = "tasks.daily")
public class TasksConfig {
    private int daysAhead = 7;
    private List<TaskTemplate> templates;

    @Data
    public static class TaskTemplate {
        private String title;
        private String description;
    }
}