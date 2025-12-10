package ru.eltech.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import com.fasterxml.jackson.annotation.JsonIgnore; // ← ДОБАВЬТЕ

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "workers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"schedules", "rooms"})
@DynamicUpdate
public class Worker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "login", nullable = false, unique = true)
    private String login;

    @Column(name = "shift", nullable = false)
    private String shift = "day"; // значения: "day", "night", "evening", "flexible"

    // Связь с графиком работы
    @OneToMany(mappedBy = "worker", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<WorkerSchedule> schedules = new HashSet<>();

    // Связь с комнатами
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "worker_rooms",
            joinColumns = @JoinColumn(name = "worker_id"),
            inverseJoinColumns = @JoinColumn(name = "room_id")
    )
    @JsonIgnore
    private Set<Room> rooms = new HashSet<>();
}