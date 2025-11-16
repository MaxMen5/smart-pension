package ru.eltech.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.eltech.dto.AuthResponse;
import ru.eltech.repositories.UserRepository;
import ru.eltech.entity.User;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthResponse authenticateUser(String login, String password) {
        Optional<User> userOptional = userRepository.findByLogin(login);

        if (userOptional.isEmpty()) {
            return new AuthResponse(false, "Пользователь не найден", null, null);
        }

        User user = userOptional.get();

        if (!passwordEncoder.matches(password, user.getPassUser())) {
            return new AuthResponse(false, "Неверный пароль", null, null);
        }

        return new AuthResponse(true, "Авторизация успешна", user.getRoleUser(), user.getLogin());
    }
}