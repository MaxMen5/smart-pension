package ru.eltech.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.eltech.dto.AuthDto;
import ru.eltech.dto.LoginDto;
import ru.eltech.services.AuthService;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public AuthDto login(@RequestBody LoginDto request) {
        return authService.authenticateUser(request.login(), request.password());
    }

    @PostMapping("/logout")
    public Map<String, String> logout(@RequestHeader("Authorization") String authHeader) {
        authService.logoutUserByToken(authHeader);
        return (Map.of("message", "Logout successful"));
    }

}