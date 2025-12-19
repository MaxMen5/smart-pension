package ru.eltech.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@DynamicUpdate
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long idUser;

    @Column(name = "login", nullable = false, unique = true)
    private String login;

    @Column(name = "pass", nullable = false)
    private String passUser;

    @Column(name = "role_user", nullable = false)
    private String roleUser;

    @Column(name = "is_active")
    private Boolean isActive;
}