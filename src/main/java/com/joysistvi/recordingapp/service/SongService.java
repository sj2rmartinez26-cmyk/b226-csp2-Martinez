package com.joysistvi.recordingapp.service;

import com.joysistvi.recordingapp.model.Song;
import com.joysistvi.recordingapp.repository.SongRepository;

import java.util.List;

public class SongService {
    private final SongRepository songRepository;

    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public List<Song> getAllSongs() {
        return songRepository.findAll();
    }

    public boolean addSong(String title, String length, String genre, int albumId) {
        if (title == null || title.isBlank()) {
            System.out.println("Validation Error: Song title cannot be empty.");
            return false;
        }
        Song song = new Song(0, title, length, genre, albumId, 0);
        return songRepository.create(song);
    }

    public boolean updateSong(int id, String title, String length, String genre, int albumId) {
        Song existing = songRepository.findById(id);
        if (existing == null) {
            System.out.println("Validation Error: Song not found with ID " + id);
            return false;
        }
        Song song = new Song(id, title, length, genre, albumId, 0);
        return songRepository.update(song);
    }

    public boolean removeSong(int id) {
        return songRepository.delete(id);
    }
}