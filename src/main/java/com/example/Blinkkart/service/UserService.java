package com.example.Blinkkart.service;

import com.example.Blinkkart.model.User;

public interface UserService {
    User registerUser(User user);
    User loginUser(String email, String password);

    User findById(Long userId);
}
