package com.joysistvi.recordingapp.view;

import com.joysistvi.recordingapp.model.User;
import java.util.List;

public class UserView {
    public void displayUsers(List<User> users) {
        System.out.println("+----+------------------------------+");
        System.out.printf("| %-2s | %-28s |%n", "ID", "Username");
        System.out.println("+----+------------------------------+");
        for (User u : users) {
            System.out.printf("| %-2d | %-28s |%n", u.getId(), u.getUsername());
        }
        System.out.println("+----+------------------------------+");
    }
}