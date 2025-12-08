package ru.eltech.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.eltech.dto.CreateUserRequest;
import ru.eltech.dto.UserDto;
import ru.eltech.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/find_all")
    public List<UserDto> findAll() {
        return userService.getAllUsers();
    }

    @PostMapping("/create")
    public ResponseEntity<UserDto> create(@RequestBody CreateUserRequest userRequest) {
        UserDto user = userService.create(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam Long userId) {
        userService.delete(userId);
    }
}
