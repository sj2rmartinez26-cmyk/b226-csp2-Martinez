package com.joysistvi.recordingapp.repository;

import com.joysistvi.recordingapp.model.User;
import java.util.List;

public interface UserRepository {
    List<User> findAll();
    User findById(int id);
    User findByUsername(String username);
    boolean create(User user);
    boolean delete(int id);

    boolean updateRole(int userId, String newRole);
}