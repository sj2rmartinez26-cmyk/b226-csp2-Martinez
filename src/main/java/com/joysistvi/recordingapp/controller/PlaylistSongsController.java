package com.joysistvi.recordingapp.controller;

import com.joysistvi.recordingapp.model.Song;
import com.joysistvi.recordingapp.service.PlaylistSongsService;
import java.util.List;

public class PlaylistSongsController {
    private final PlaylistSongsService service;

    public PlaylistSongsController(PlaylistSongsService service) {
        this.service = service;
    }

    public boolean addSongToPlaylist(int playlistId, int songId) { return service.addSong(playlistId, songId); }
    public boolean removeSongFromPlaylist(int playlistId, int songId) { return service.removeSong(playlistId, songId); }
    public List<Song> getPlaylistSongs(int playlistId) { return service.getSongs(playlistId); }
}