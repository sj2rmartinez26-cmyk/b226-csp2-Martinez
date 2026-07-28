package com.joysistvi.recordingapp.controller;

import com.joysistvi.recordingapp.model.Playlist;
import com.joysistvi.recordingapp.service.PlaylistService;
import java.util.List;

public class PlaylistController {
    private final PlaylistService playlistService;

    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    public List<Playlist> getAll() { return playlistService.getAllPlaylists(); }
    public boolean create(String name, int userId) { return playlistService.addPlaylist(name, userId); }
    public boolean update(int id, String name, int userId) { return playlistService.updatePlaylist(id, name, userId); }
    public boolean delete(int id) { return playlistService.deletePlaylist(id); }
}