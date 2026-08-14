package com.labelai.backend.controller;

import com.labelai.backend.entity.User;
import com.labelai.backend.security.JwtService;
import com.labelai.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;

    public AuthController(
            UserService userService,
            JwtService jwtService
    ) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.register(user);
    }

    @GetMapping("/users")
    public java.util.List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody Map<String, String> request
    ) {

        String email = request.get("email");
        String password = request.get("password");

        return userService.login(email, password)
                .map(user -> {

                    // Generate JWT token
                    String token = jwtService.generateToken(user.getEmail());

                    Map<String, Object> response = new HashMap<>();

                    response.put("message", "Login successful");
                    response.put("token", token);
                    response.put("id", user.getId());
                    response.put("name", user.getName());
                    response.put("email", user.getEmail());
                    response.put("role", user.getRole());

                    return ResponseEntity.ok(response);
                })
                .orElseGet(() ->
                        ResponseEntity.status(401)
                                .body(Map.of(
                                        "message",
                                        "Invalid email or password"
                                ))
                );
    }
}