package com.labelai.backend.service;

import com.labelai.backend.entity.User;
import com.labelai.backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

   private final UserRepository userRepository;
private final PasswordEncoder passwordEncoder;

public UserService(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder
) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
}

    // Create / Register User
    public User createUser(User user) {

    if (userRepository.existsByEmail(user.getEmail())) {
        throw new RuntimeException("Email already registered");
    }

    user.setPassword(passwordEncoder.encode(user.getPassword()));

    return userRepository.save(user);
}

    // Register User - kept for compatibility
    public User register(User user) {
        return createUser(user);
    }

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get user by ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Get user by email
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public Optional<User> login(String email, String password) {

    Optional<User> userOptional = userRepository.findByEmail(email);

    if (userOptional.isPresent()) {

        User user = userOptional.get();

        if (passwordEncoder.matches(password, user.getPassword())) {
            return Optional.of(user);
        }
    }

    return Optional.empty();
}

    // Delete user
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}