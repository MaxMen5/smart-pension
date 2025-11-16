package ru.eltech.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.eltech.dto.LoginRequest;
import ru.eltech.dto.AuthResponse;
import ru.eltech.services.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        System.out.println("Попытка авторизации: " + loginRequest.getLogin());

        AuthResponse authResponse = authService.authenticateUser(
                loginRequest.getLogin(),
                loginRequest.getPassword()
        );

        if (authResponse.isSuccess()) {
            System.out.println("Успешная авторизация: " + loginRequest.getLogin());
        } else {
            System.out.println("Ошибка авторизации: " + authResponse.getMessage());
        }

        return ResponseEntity.ok(authResponse);
    }
}