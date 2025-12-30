package ru.eltech.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.eltech.entity.User;
import ru.eltech.repositories.UserRepository;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (!userRepository.existsByLogin("start")) {
            User user = new User();
            user.setLogin("start");
            user.setPassUser(passwordEncoder.encode("123456"));
            user.setRoleUser("администратор");
            user.setIsActive(false);
            userRepository.save(user);
        }
    }
}