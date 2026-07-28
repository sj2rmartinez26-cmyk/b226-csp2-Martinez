package com.joysistvi.recordingapp.controller;

import com.joysistvi.recordingapp.model.Song;
import com.joysistvi.recordingapp.service.SongService;

import java.util.List;

public class SongController {
    private final SongService songService;

    public SongController(SongService songService) {
        this.songService = songService;
    }

    public List<Song> getAllSongs() {
        return songService.getAllSongs();
    }

    public boolean createSong(String title, String length, String genre, int albumId) {
        return songService.addSong(title, length, genre, albumId);
    }

    public boolean updateSong(int id, String title, String length, String genre, int albumId) {
        return songService.updateSong(id, title, length, genre, albumId);
    }

    public boolean deleteSong(int id) {
        return songService.removeSong(id);
    }
}