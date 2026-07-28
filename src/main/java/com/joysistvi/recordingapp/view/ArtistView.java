package com.joysistvi.recordingapp.view;

import com.joysistvi.recordingapp.model.Artist;
import java.util.List;

public class ArtistView {
    public void displayArtists(List<Artist> artists) {
        System.out.println("+----+------------------------------+--------------------+");
        System.out.printf("| %-2s | %-28s | %-18s |%n", "ID", "Name", "Genre");
        System.out.println("+----+------------------------------+--------------------+");
        for (Artist a : artists) {
            System.out.printf("| %-2d | %-28s | %-18s |%n", a.getId(), a.getName(), a.getGenre());
        }
        System.out.println("+----+------------------------------+--------------------+");
    }
}