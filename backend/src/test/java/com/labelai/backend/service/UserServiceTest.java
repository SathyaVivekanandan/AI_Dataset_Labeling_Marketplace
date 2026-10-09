package com.labelai.backend.service;

import com.labelai.backend.entity.Role;
import com.labelai.backend.entity.User;
import com.labelai.backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User owner;
    private User annotator;

    @BeforeEach
    void setUp() {
        lenient().when(passwordEncoder.encode(anyString())).thenReturn("$2a$hashed-password");
        owner = new User();
        owner.setId(1L);
        owner.setName("Owner User");
        owner.setEmail("owner@example.com");
        owner.setPassword("password123");
        owner.setRole(Role.OWNER);

        annotator = new User();
        annotator.setId(2L);
        annotator.setName("Annotator User");
        annotator.setEmail("annotator@example.com");
        annotator.setPassword("password123");
        annotator.setRole(Role.ANNOTATOR);
    }

    @Test
    void createUser_ShouldCreateUserSuccessfully() {
        when(userRepository.existsByEmail(owner.getEmail())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(owner);

        User result = userService.createUser(owner);

        assertNotNull(result);
        assertEquals("owner@example.com", result.getEmail());
        assertEquals(Role.OWNER, result.getRole());

        verify(userRepository, times(1)).existsByEmail(owner.getEmail());
        verify(userRepository, times(1)).save(owner);
    }

    @Test
    void createUser_ShouldThrowException_WhenEmailAlreadyExists() {
        when(userRepository.existsByEmail(owner.getEmail())).thenReturn(true);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> userService.createUser(owner)
        );

        assertEquals("Email already registered", exception.getMessage());

        verify(userRepository, times(1)).existsByEmail(owner.getEmail());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void createAnnotator_ShouldCreateAnnotatorSuccessfully() {
        when(userRepository.existsByEmail(annotator.getEmail())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(annotator);

        User result = userService.createUser(annotator);

        assertNotNull(result);
        assertEquals("annotator@example.com", result.getEmail());
        assertEquals(Role.ANNOTATOR, result.getRole());

        verify(userRepository, times(1)).existsByEmail(annotator.getEmail());
        verify(userRepository, times(1)).save(annotator);
    }

    @Test
    void getAllUsers_ShouldReturnUsers() {
        when(userRepository.findAll()).thenReturn(java.util.List.of(owner, annotator));
        assertEquals(2, userService.getAllUsers().size());
        verify(userRepository).findAll();
    }

    @Test
    void getUserById_ShouldReturnUserWhenFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(owner));
        assertTrue(userService.getUserById(1L).isPresent());
        verify(userRepository).findById(1L);
    }

    @Test
    void login_ShouldReturnUserWhenPasswordMatches() {
        when(userRepository.findByEmail(owner.getEmail())).thenReturn(Optional.of(owner));
        when(passwordEncoder.matches("password123", owner.getPassword())).thenReturn(true);
        assertTrue(userService.login(owner.getEmail(), "password123").isPresent());
    }

    @Test
    void login_ShouldRejectWrongPassword() {
        when(userRepository.findByEmail(owner.getEmail())).thenReturn(Optional.of(owner));
        when(passwordEncoder.matches("wrong-password", owner.getPassword())).thenReturn(false);
        assertTrue(userService.login(owner.getEmail(), "wrong-password").isEmpty());
    }

    @Test
    void deleteUser_ShouldDeleteById() {
        userService.deleteUser(1L);
        verify(userRepository).deleteById(1L);
    }

    @Test
    void findUserByEmail_ShouldReturnUser_WhenUserExists() {
        when(userRepository.findByEmail(owner.getEmail()))
                .thenReturn(Optional.of(owner));

        Optional<User> result = userService.getUserByEmail(owner.getEmail());

        assertTrue(result.isPresent());
        assertEquals(owner.getEmail(), result.get().getEmail());
        assertEquals(Role.OWNER, result.get().getRole());

        verify(userRepository, times(1)).findByEmail(owner.getEmail());
    }
}

