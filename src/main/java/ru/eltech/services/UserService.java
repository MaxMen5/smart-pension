package ru.eltech.services;

import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.eltech.dto.CreateUserDto;
import ru.eltech.dto.UserDto;
import ru.eltech.entity.User;
import ru.eltech.entity.Worker;
import ru.eltech.exception.MyException;
import ru.eltech.repositories.UserRepository;
import ru.eltech.repositories.WorkerRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final WorkerRepository workerRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, WorkerRepository workerRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.workerRepository = workerRepository;
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

    @Transactional
    public UserDto create(CreateUserDto userRequest) {

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

        if (user.getRoleUser().equals("сиделка")) {
            Worker worker = new Worker();
            worker.setLogin(userRequest.login());

            workerRepository.save(worker);
        }

        return new UserDto(user.getIdUser(), user.getLogin(), user.getRoleUser());
    }

    @Transactional
    public void delete(Long idUser) {
        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new MyException("Пользователь не найден"));

        if ("сиделка".equals(user.getRoleUser())) {
            Optional<Worker> worker = workerRepository.findByLogin(user.getLogin());
            worker.ifPresent(workerRepository::delete);
        }

        userRepository.delete(user);
    }
}
