package ru.itis.semesterwork.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.itis.semesterwork.entity.User;
import ru.itis.semesterwork.repository.Repository;
import ru.itis.semesterwork.repository.UserRepositoryImpl;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class UserService {
    private UserRepositoryImpl userRepository;
    private PasswordEncoder encoder;

    public List<User> getAll(int offset, int limit) {
        return userRepository.findAll(offset, limit);
    }
    public Integer countAll() {
        return userRepository.findAll().size();
    }
    public Optional<User> getBySessionId(String sessionId) {
        return userRepository.findByAttribute("session_id", sessionId);
    }
    public Optional<User> getById(Integer id) {
        return userRepository.findByAttribute("id", id);
    }
    public Optional<User> getByRememberId(String rememberId) {
        return userRepository.findByAttribute("remember_id", rememberId);
    }

    public Optional<User> getByNameAndPass(String username, String password) {
        Optional<User> user = userRepository.findByAttribute("username", username);
        if (user.isPresent() && encoder.matches(password, user.get().getPassword())) {
            return user;
        } else {
            return Optional.empty();
        }
    }
    public Optional<User> getByUsername(String username) {
        return userRepository.findByAttribute("username", username);
    }
    public Optional<User> getByEmail(String email) {
        return userRepository.findByAttribute("email", email);
    }
    public boolean addSkillPoint(User user, Integer skillPoint) {
        return userRepository.addSkillPoint(user, skillPoint);
    }
    public boolean update(User user) {
        return userRepository.update(user);
    }
    public boolean add(User user) {
        return userRepository.insert(user);
    }
    public boolean updateAttribute(User user, String attribute, Object value) {
        return userRepository.updateAttribute(user, attribute, value);
    }
}
