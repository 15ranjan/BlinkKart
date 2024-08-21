package com.example.Blinkkart.controller;

import com.example.Blinkkart.model.User;
import com.example.Blinkkart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody User user){
        User registeredUser  = userService.registerUser(user);
        if(registeredUser != null){
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
        }else{
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Server Error");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User loginRequest ){
        User loggedInUser = userService.loginUser(loginRequest.getEmail(), loginRequest.getPassword());
        if(loggedInUser != null){
            return ResponseEntity.status(HttpStatus.OK).body("Login successful");
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}
