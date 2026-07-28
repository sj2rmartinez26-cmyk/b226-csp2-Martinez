package com.joysistvi.recordingapp.service;

import com.joysistvi.recordingapp.model.Song;
import com.joysistvi.recordingapp.repository.PlaylistRepository;
import com.joysistvi.recordingapp.repository.PlaylistSongsRepository;
import com.joysistvi.recordingapp.repository.SongRepository;
import java.util.List;

public class PlaylistSongsService {
    private final PlaylistSongsRepository playlistSongsRepository;
    private final PlaylistRepository playlistRepository;
    private final SongRepository songRepository;

    public PlaylistSongsService(PlaylistSongsRepository psRepo, PlaylistRepository pRepo, SongRepository sRepo) {
        this.playlistSongsRepository = psRepo;
        this.playlistRepository = pRepo;
        this.songRepository = sRepo;
    }

    public boolean addSong(int playlistId, int songId) {
        if (playlistRepository.findById(playlistId) == null || songRepository.findById(songId) == null) return false;
        return playlistSongsRepository.addSongToPlaylist(playlistId, songId);
    }

    public boolean removeSong(int playlistId, int songId) {
        return playlistSongsRepository.removeSongFromPlaylist(playlistId, songId);
    }

    public List<Song> getSongs(int playlistId) {
        return playlistSongsRepository.getSongsInPlaylist(playlistId);
    }
}