package ru.eltech.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor // Эта аннотация автоматически создаст конструктор со всеми полями
@ToString
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
}