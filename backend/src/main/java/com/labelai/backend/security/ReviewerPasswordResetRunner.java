
package com.labelai.backend.security;

import com.labelai.backend.entity.Role;
import com.labelai.backend.entity.User;
import com.labelai.backend.repository.UserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.Console;
import java.util.Scanner;

@Component
@Profile("reset-reviewer")
public class ReviewerPasswordResetRunner implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ReviewerPasswordResetRunner(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        Console console = System.console();

        if (console == null) {
            System.err.println(
                "Run this in a real terminal, not the IDE output console."
            );
            return;
        }

        String email = console.readLine("Reviewer email: ");
        char[] password = console.readPassword(
            "Enter new password (minimum 8 characters): "
        );

        try {
            String newPassword = new String(password);

            if (newPassword.length() < 8) {
                System.err.println("Password must be at least 8 characters.");
                return;
            }

            User user = userRepository.findByEmail(email.trim())
                .orElseThrow(() ->
                    new IllegalArgumentException("Account not found."));

            if (user.getRole() != Role.REVIEWER) {
                System.err.println("This account is not a REVIEWER.");
                return;
            }

            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);

            System.out.println("Reviewer password reset successfully.");
        } finally {
            java.util.Arrays.fill(password, '\0');
        }
    }
}
