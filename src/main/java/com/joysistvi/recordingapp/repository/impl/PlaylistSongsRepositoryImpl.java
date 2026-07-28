package com.joysistvi.recordingapp.repository.impl;

import com.joysistvi.recordingapp.config.DbConnection;
import com.joysistvi.recordingapp.model.Song;
import com.joysistvi.recordingapp.repository.PlaylistSongsRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaylistSongsRepositoryImpl implements PlaylistSongsRepository {
    private final DbConnection dbConnection;

    public PlaylistSongsRepositoryImpl(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public boolean addSongToPlaylist(int playlistId, int songId) {
        String query = "INSERT INTO playlist_songs (playlist_id, song_id) VALUES (?, ?)";
        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {
            prep.setInt(1, playlistId);
            prep.setInt(2, songId);
            return prep.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error adding song to playlist: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean removeSongFromPlaylist(int playlistId, int songId) {
        String query = "DELETE FROM playlist_songs WHERE playlist_id = ? AND song_id = ?";
        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {
            prep.setInt(1, playlistId);
            prep.setInt(2, songId);
            return prep.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error removing song from playlist: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Song> getSongsInPlaylist(int playlistId) {
        List<Song> songs = new ArrayList<>();
        String query = "SELECT s.* FROM songs s JOIN playlist_songs ps ON s.id = ps.song_id WHERE ps.playlist_id = ?";
        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {
            prep.setInt(1, playlistId);
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                songs.add(new Song(rs.getInt("id"), rs.getString("title"), rs.getString("length"), rs.getString("genre"), rs.getInt("album_id"), rs.getInt("is_archived")));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving playlist songs: " + e.getMessage());
        }
        return songs;
    }
}