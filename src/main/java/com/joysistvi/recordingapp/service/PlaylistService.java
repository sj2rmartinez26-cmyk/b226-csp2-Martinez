package com.joysistvi.recordingapp.service;

import com.joysistvi.recordingapp.model.Playlist;
import com.joysistvi.recordingapp.repository.PlaylistRepository;
import com.joysistvi.recordingapp.repository.UserRepository;
import java.util.List;

public class PlaylistService {
    private final PlaylistRepository playlistRepository;
    private final UserRepository userRepository;

    public PlaylistService(PlaylistRepository playlistRepository, UserRepository userRepository) {
        this.playlistRepository = playlistRepository;
        this.userRepository = userRepository;
    }

    public List<Playlist> getAllPlaylists() { return playlistRepository.findAll(); }

    public boolean addPlaylist(String name, int userId) {
        if (userRepository.findById(userId) == null) return false;
        return playlistRepository.create(new Playlist(0, name, userId));
    }

    public boolean updatePlaylist(int id, String name, int userId) {
        if (playlistRepository.findById(id) == null || userRepository.findById(userId) == null) return false;
        return playlistRepository.update(new Playlist(id, name, userId));
    }

    public boolean deletePlaylist(int id) { return playlistRepository.delete(id); }
}