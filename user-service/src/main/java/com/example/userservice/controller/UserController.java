package com.example.userservice.controller;

import com.example.userservice.entities.User;
import com.example.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/*
    @author: Dinh Quang Anh
    Date   : 9/6/2023
    Project: user-service
*/
@RestController
@CrossOrigin()
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/save")
    public User save(@RequestBody User user) {
        return userService.save(user);
    }

    @GetMapping(value = "/get")
    public User getUser(@RequestParam long userId) {
        return userService.getById(userId);
    }

    @GetMapping(value = "/getByEmail")
    public User getUser(@RequestParam String email) {
        return userService.getUserByEmail(email);
    }

    @GetMapping(value = "/secure")
    public String getSecure() {
        return "Secure endpoint available";
    }
}
