package com.joysistvi.recordingapp.view;

import com.joysistvi.recordingapp.model.Playlist;
import java.util.List;

public class PlaylistView {
    public void displayPlaylists(List<Playlist> playlists) {
        System.out.println("+----+------------------------------+---------+");
        System.out.printf("| %-2s | %-28s | %-7s |%n", "ID", "Playlist Name", "User ID");
        System.out.println("+----+------------------------------+---------+");
        for (Playlist p : playlists) {
            System.out.printf("| %-2d | %-28s | %-7d |%n", p.getId(), p.getName(), p.getUserId());
        }
        System.out.println("+----+------------------------------+---------+");
    }
}