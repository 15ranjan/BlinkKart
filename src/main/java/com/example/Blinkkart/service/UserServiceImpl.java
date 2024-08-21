package com.example.Blinkkart.service;

import com.example.Blinkkart.model.User;
import com.example.Blinkkart.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public User registerUser(User user) {
        try{
            if (userRepository.existsByEmail(user.getEmail())) {
                return null; // Email already in use
            }
            return userRepository.save(user);
        }catch(Exception e){
            System.err.println("Error registering user: " + e.getMessage());
            return null; // Registration failed
        }
    }

    @Override
    public User loginUser(String email, String password) {
        try {
            User user = userRepository.findByEmail(email);
            if (user != null && user.getPassword().equals(password)) {
                return user; // Successful login
            }
            return null; // Invalid credentials
        } catch (Exception e) {
            // Log the error and handle it gracefully
            System.err.println("Error logging in user: " + e.getMessage());
            return null; // Login failed
        }
    }

    @Override
    public User findById(Long userId) {
        try {
            return userRepository.findById(userId).orElse(null); // Return null if user not found
        } catch (Exception e) {
            // Log the error and handle it
            System.err.println("Error finding user by ID: " + e.getMessage());
            return null; // Or throw an appropriate exception
        }
    }
}
