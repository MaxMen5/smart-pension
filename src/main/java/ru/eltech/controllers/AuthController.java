package ru.eltech.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.eltech.dto.auth.AuthDto;
import ru.eltech.dto.auth.LoginDto;
import ru.eltech.services.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    AuthController(AuthService authService) { this.authService = authService; }

    @PostMapping("/login")
    public AuthDto login(@RequestBody LoginDto loginDto) {
        return authService.authenticateUser(loginDto.login(), loginDto.password());
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String authHeader) {
        authService.logoutUserByToken(authHeader);
        return ResponseEntity.noContent().build();
    }
}