package com.joysistvi.recordingapp.view;

import com.joysistvi.recordingapp.model.Song;
import java.util.List;

public class SongView {

    public void displaySongs(List<Song> songs) {
        if (songs.isEmpty()) {
            System.out.println("No songs available.");
            return;
        }

        System.out.println("+----+------------------------------+----------+----------------+----------------+");
        System.out.printf("| %-2s | %-28s | %-8s | %-14s | %-14s |%n", "ID", "Title", "Length", "Genre", "Album ID");
        System.out.println("+----+------------------------------+----------+----------------+----------------+");

        for (Song s : songs) {
            System.out.printf("| %-2d | %-28s | %-8s | %-14s | %-14d |%n",
                    s.getId(), s.getTitle(), s.getLength(), s.getGenre(), s.getAlbumId());
        }
        System.out.println("+----+------------------------------+----------+----------------+----------------+");
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }
}