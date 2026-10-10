
package com.labelai.backend.service;

import com.labelai.backend.entity.Role;
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
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Create / Register User
    public User createUser(User user) {

        if (user.getName() == null || user.getName().isBlank()
                || user.getEmail() == null
                || !user.getEmail().matches(
                        "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")
                || user.getPassword() == null
                || user.getPassword().length() < 8) {
            throw new IllegalArgumentException(
                    "Provide a name, valid email, and password of at least 8 characters");
        }

        if (user.getRole() == null
                || (user.getRole() != Role.OWNER
                && user.getRole() != Role.ANNOTATOR)) {
            throw new IllegalArgumentException(
                    "Public registration is limited to OWNER and ANNOTATOR roles");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException(
                    "Email already registered");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    // Register User - compatibility
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

    // Login
    public Optional<User> login(String email, String password) {

        if (email == null || email.isBlank()
                || password == null) {
            return Optional.empty();
        }

        Optional<User> userOptional =
                userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            if (passwordEncoder.matches(
                    password, user.getPassword())) {
                return Optional.of(user);
            }
        }

        return Optional.empty();
    }

    // Reset password for an existing reviewer
    public boolean resetReviewerPassword(
            String email, String newPassword) {

        if (email == null || email.isBlank()
                || newPassword == null
                || newPassword.length() < 8) {
            throw new IllegalArgumentException(
                    "Valid email and password of at least 8 characters required");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "User not found"));

        if (user.getRole() != Role.REVIEWER) {
            throw new IllegalArgumentException(
                    "This account is not a reviewer");
        }

        user.setPassword(
                passwordEncoder.encode(newPassword));

        userRepository.save(user);

        return true;
    }

    // Delete user
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
