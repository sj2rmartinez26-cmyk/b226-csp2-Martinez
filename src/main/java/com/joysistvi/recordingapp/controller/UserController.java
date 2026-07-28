package com.joysistvi.recordingapp.controller;

import com.joysistvi.recordingapp.model.User;
import com.joysistvi.recordingapp.service.UserService;
import java.util.List;

public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public List<User> getAll() { return userService.getAllUsers(); }
    public boolean register(String username, String password) { return userService.register(username, password); }
    public boolean login(String username, String password) { return userService.login(username, password); }
    public boolean delete(int id) { return userService.deleteUser(id); }

    public boolean updateRole(int userId, String newRole) {
        return userService.updateRole(userId, newRole);
    }


    }

