package com.joysistvi.recordingapp.view;

import com.joysistvi.recordingapp.model.Album;
import java.util.List;

public class AlbumView {
    public void displayAlbums(List<Album> albums) {
        System.out.println("+----+------------------------------+------+-----------+");
        System.out.printf("| %-2s | %-28s | %-4s | %-9s |%n", "ID", "Title", "Year", "Artist ID");
        System.out.println("+----+------------------------------+------+-----------+");
        for (Album a : albums) {
            System.out.printf("| %-2d | %-28s | %-4d | %-9d |%n", a.getId(), a.getTitle(), a.getReleaseYear(), a.getArtistId());
        }
        System.out.println("+----+------------------------------+------+-----------+");
    }
}