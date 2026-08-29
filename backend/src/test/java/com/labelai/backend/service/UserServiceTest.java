package com.labelai.backend.service;

import com.labelai.backend.entity.Role;
import com.labelai.backend.entity.User;
import com.labelai.backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock UserRepository repo;
    UserService service;
    @BeforeEach void setUp() { service = new UserService(repo, new BCryptPasswordEncoder()); }

    @Test void createUser_hashesPassword() {
        User u = new User("Test", "test@example.com", "secret", Role.LABELER);
        when(repo.existsByEmail(u.getEmail())).thenReturn(false); when(repo.save(any(User.class))).thenAnswer(i -> i.getArgument(0));
        User saved = service.createUser(u);
        assertNotEquals("secret", saved.getPassword());
        assertTrue(new BCryptPasswordEncoder().matches("secret", saved.getPassword()));
        verify(repo).save(u);
    }
    @Test void createUser_rejectsDuplicate() {
        User u = new User("Test", "test@example.com", "secret", Role.LABELER);
        when(repo.existsByEmail(u.getEmail())).thenReturn(true);
        assertThrows(RuntimeException.class, () -> service.createUser(u));
        verify(repo, never()).save(any());
    }
    @Test void login_acceptsCorrectPassword() {
        User u = new User("Test", "test@example.com", new BCryptPasswordEncoder().encode("secret"), Role.LABELER);
        when(repo.findByEmail(u.getEmail())).thenReturn(Optional.of(u));
        assertTrue(service.login(u.getEmail(), "secret").isPresent());
    }
    @Test void login_rejectsWrongPassword() {
        User u = new User("Test", "test@example.com", new BCryptPasswordEncoder().encode("secret"), Role.LABELER);
        when(repo.findByEmail(u.getEmail())).thenReturn(Optional.of(u));
        assertTrue(service.login(u.getEmail(), "bad").isEmpty());
    }
}
