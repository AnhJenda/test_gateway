package com.example.userservice.controller;

import com.example.userservice.entities.*;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.service.AuthService;
import com.example.userservice.service.JwtUtils;
import com.example.userservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/*
    @author: Dinh Quang Anh
    Date   : 9/8/2023
    Project: user-service
*/
@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    private JwtUtils jwt;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository repository;
    @Autowired
    private UserService userService;


    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        User userOptional = repository.findUserByEmail(loginRequest.getUsername());

        if (Objects.nonNull(userOptional)) {
            if (passwordEncoder.matches(loginRequest.getPassword(), userOptional.getPassword())) {
                return ResponseEntity.ok(new JwtResponse(
                        jwt.generateToken(userOptional.getEmail()),
                        userOptional.getId(),
                        userOptional.getEmail(),
                        userOptional.getFirstName(),
                        userOptional.getLastName(),
                        userOptional.getRole())
                );
            }
        }
        return ResponseEntity.badRequest().body("user not found");
    }


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (repository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        String hashedPassword = passwordEncoder.encode(signUpRequest.getPassword());

        User user = new User();
        user.setPassword(hashedPassword);
        user.setEmail(signUpRequest.getEmail());
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setRole(signUpRequest.getRole());
        repository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
