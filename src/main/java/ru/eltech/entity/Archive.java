package ru.eltech.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import java.time.LocalDate;

@Entity
@Table(name = "residents_archive")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@DynamicUpdate
public class Archive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_archive")
    private Long idArchive;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "passport_number", nullable = false)
    private String passportNumber;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "admission_date", nullable = false)
    private LocalDate admissionDate;

    @Column(name = "room_number", nullable = false)
    private String roomNumber;

    @Column(name = "archive_date", nullable = false)
    private LocalDate archiveDate;

    @Column(name = "archive_reason", nullable = false)
    private String archiveReason;
}
