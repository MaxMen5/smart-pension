package ru.eltech.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.eltech.dto.auth.AuthDto;
import ru.eltech.exception.MyException;
import ru.eltech.repositories.UserRepository;
import ru.eltech.entity.User;
import ru.eltech.security.JwtUtil;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    public AuthDto authenticateUser(String login, String password) {
        Optional<User> userOptional = userRepository.findByLogin(login);
        if (userOptional.isEmpty()) throw new MyException("Пользователь не найден!");

        User user = userOptional.get();
        if (!passwordEncoder.matches(password, user.getPassUser())) throw new MyException("Неверный пароль");

        String token = jwtUtil.generateToken(login);

        return new AuthDto(user.getRoleUser(), token);
    }
}