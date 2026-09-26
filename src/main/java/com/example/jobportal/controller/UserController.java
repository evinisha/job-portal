package com.example.jobportal.controller;

import com.example.jobportal.repository.UserRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/api/users")
    public List<Map<String, Object>> getUsers() {

        return userRepository.getAllUsers();
    }
    @PostMapping("/api/users/register")
    public String registerUser(@RequestBody Map<String, Object> user) {

        String name = (String) user.get("name");
        String email = (String) user.get("email");
        String password = (String) user.get("password");
        String role = (String) user.get("role");

        int result = userRepository.registerUser(
                name, email, password, role
        );

        if (result == 0) {
            return "Email already registered!";
        }

        return "User registered successfully!";
    }
    @PostMapping("/api/users/login")
    public Object loginUser(@RequestBody Map<String, Object> user) {

        String email = (String) user.get("email");
        String password = (String) user.get("password");

        Map<String, Object> loggedInUser =
                userRepository.loginUser(email, password);

        if (loggedInUser == null) {
            return "Invalid email or password!";
        }

        return loggedInUser;
    }
}