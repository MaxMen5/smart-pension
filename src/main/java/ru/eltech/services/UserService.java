package ru.eltech.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.eltech.dto.CreateUserRequest;
import ru.eltech.dto.UserDto;
import ru.eltech.entity.User;
import ru.eltech.exception.MyException;
import ru.eltech.repositories.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(user -> new UserDto(
                        user.getIdUser(),
                        user.getLogin(),
                        user.getRoleUser()
                ))
                .collect(Collectors.toList());
    }

    public UserDto create(CreateUserRequest userRequest) {

        if (userRepository.existsByLogin(userRequest.login())) {
            throw new MyException("Пользователь с логином " + userRequest.login() + " уже существует");
        }

        User user = new User();

        user.setLogin(userRequest.login());

        String hashedPassword = passwordEncoder.encode(userRequest.passUser());
        user.setPassUser(hashedPassword);

        user.setRoleUser(userRequest.roleUser());
        user.setIsActive(false);

        userRepository.save(user);
        return new UserDto(user.getIdUser(), user.getLogin(), user.getRoleUser());
    }

    public void delete(Long idUser) {
        User user = userRepository.findById(idUser).orElse(null);
        userRepository.delete(user);
    }
}
