package ru.eltech.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.eltech.exception.MyException;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "rooms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@DynamicUpdate
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "room_number", nullable = false)
    private String roomNumber;

    @Column(name = "room_type", nullable = false)
    private String roomType;

    @Column(name = "free_spots", nullable = false)
    private Integer freeSpots;

    @ManyToMany(mappedBy = "rooms", fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude
    private Set<Worker> workers = new HashSet<>();


    public void decrementFreeSpots() {
        if (this.freeSpots <= 0) throw new MyException("В комнате нет свободных мест");
        this.freeSpots--;
    }

    public void incrementFreeSpots() {
        if (this.freeSpots >= 3) throw new MyException("В комнате и так все места свободны");
        this.freeSpots++;
    }
}