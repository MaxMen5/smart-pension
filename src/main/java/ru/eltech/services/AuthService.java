package ru.eltech.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.eltech.dto.AuthResponse;
import ru.eltech.repositories.UserRepository;
import ru.eltech.entity.User;
import ru.eltech.security.JwtUtil;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Transactional
    public AuthResponse authenticateUser(String login, String password) {
        Optional<User> userOptional = userRepository.findByLogin(login);

        if (userOptional.isEmpty()) {
            return new AuthResponse(false, "Пользователь не найден", null, null, null);
        }

        User user = userOptional.get();

        if (!passwordEncoder.matches(password, user.getPassUser())) {
            return new AuthResponse(false, "Неверный пароль", null, null, null);
        }

        user.setIsActive(true);
        userRepository.save(user);

        String token = jwtUtil.generateToken(login);

        return new AuthResponse(true, "Авторизация успешна",
                user.getRoleUser(), user.getLogin(), token);
    }

    @Transactional
    public void logoutUserByToken(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String username = jwtUtil.extractUsername(token);

            Optional<User> userOptional = userRepository.findByLogin(username);
            userOptional.ifPresent(user -> {
                user.setIsActive(false);
                userRepository.save(user);
            });
        }
    }
}