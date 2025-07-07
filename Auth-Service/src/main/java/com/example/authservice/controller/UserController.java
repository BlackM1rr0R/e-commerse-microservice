package com.example.authservice.controller;

import com.example.authservice.dto.LoginResponse;
import com.example.authservice.entity.User;
import com.example.authservice.jwtutil.JwtUtil;
import com.example.authservice.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.register(user);
    }
    @PostMapping(value = "/login", produces = "application/json")
    public LoginResponse login(@RequestBody User user) {
        return userService.login(user);
    }
    @GetMapping("/about/me")
    public User aboutMe(@RequestHeader("Authorization") String authHeader) {
        String token=authHeader.substring(7);
        String email=jwtUtil.getEmailFromToken(token);
        return userService.aboutMe(email);
    }
}
