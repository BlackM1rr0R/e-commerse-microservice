package com.example.authservice.controller;

import com.example.authservice.dto.LoginResponse;
import com.example.authservice.entity.User;
import com.example.authservice.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.register(user);
    }
    @PostMapping(value = "/login", produces = "application/json")
    public LoginResponse login(@RequestBody User user) {
        return userService.login(user);
    }
}
