package ru.eltech.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.eltech.dto.users.CreateUserDto;
import ru.eltech.dto.users.UserDto;
import ru.eltech.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    UserController(UserService userService) { this.userService = userService; }

    @GetMapping("/find_all")
    public List<UserDto> findAll() {
        return userService.getAllUsers();
    }

    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody CreateUserDto userRequest) {
        userService.create(userRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@RequestParam Long userId) {
        userService.delete(userId);
        return ResponseEntity.noContent().build();
    }
}
