package com.joysistvi.recordingapp.service;

import com.joysistvi.recordingapp.model.User;
import com.joysistvi.recordingapp.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import java.util.List;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() { return userRepository.findAll(); }

    public boolean register(String username, String plainPassword) {
        if (userRepository.findByUsername(username) != null) {
            System.out.println("Username already exists.");
            return false;
        }
        String hashed = BCrypt.hashpw(plainPassword, BCrypt.gensalt());
        return userRepository.create(new User(0, username, hashed, "USER"));
    }

    public boolean login(String username, String plainPassword) {
        User user = userRepository.findByUsername(username);
        if (user == null) return false;
        return BCrypt.checkpw(plainPassword, user.getPassword());
    }

    public boolean deleteUser(int id) { return userRepository.delete(id); }

    public boolean updateRole(int userId, String newRole) {
        return userRepository.updateRole(userId, newRole);}
}