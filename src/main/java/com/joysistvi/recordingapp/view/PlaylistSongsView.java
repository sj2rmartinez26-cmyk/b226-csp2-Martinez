package com.joysistvi.recordingapp.view;

import com.joysistvi.recordingapp.model.Song;
import java.util.List;

public class PlaylistSongsView {
    public void displayPlaylistSongs(int playlistId, List<Song> songs) {
        System.out.println("--- Songs in Playlist ID: " + playlistId + " ---");
        if (songs.isEmpty()) {
            System.out.println("No songs found in this playlist.");
            return;
        }
        System.out.println("+----+------------------------------+----------+----------------+");
        System.out.printf("| %-2s | %-28s | %-8s | %-14s |%n", "ID", "Title", "Length", "Genre");
        System.out.println("+----+------------------------------+----------+----------------+");
        for (Song s : songs) {
            System.out.printf("| %-2d | %-28s | %-8s | %-14s |%n", s.getId(), s.getTitle(), s.getLength(), s.getGenre());
        }
        System.out.println("+----+------------------------------+----------+----------------+");
    }
}