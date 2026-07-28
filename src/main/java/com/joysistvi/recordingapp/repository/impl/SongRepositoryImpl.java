package com.joysistvi.recordingapp.repository.impl;

import com.joysistvi.recordingapp.config.DbConnection;
import com.joysistvi.recordingapp.model.Song;
import com.joysistvi.recordingapp.repository.SongRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SongRepositoryImpl implements SongRepository {
    private final DbConnection dbConnection;

    public SongRepositoryImpl(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<Song> findAll() {
        List<Song> songs = new ArrayList<>();
        String query = "SELECT * FROM songs WHERE is_archived = 0 OR is_archived IS NULL";
        try (Connection conn = dbConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                songs.add(new Song(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("length"),
                        rs.getString("genre"),
                        rs.getInt("album_id"),
                        rs.getInt("is_archived")
                ));
            }
        } catch (SQLException e) {
            System.err.println("[!] Error fetching songs: " + e.getMessage());
        }
        return songs;
    }

    @Override
    public Song findById(int id) {
        String query = "SELECT * FROM songs WHERE id = ?";
        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, id);
            try (ResultSet rs = prep.executeQuery()) {
                if (rs.next()) {
                    return new Song(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("length"),
                            rs.getString("genre"),
                            rs.getInt("album_id"),
                            rs.getInt("is_archived")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("[!] Error finding song: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean create(Song song) {
        String query = "INSERT INTO songs (title, length, genre, album_id, is_archived) VALUES (?, ?, ?, ?, 0)";
        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setString(1, song.getTitle());

            // Format mm:ss or m:ss into HH:MM:SS
            String rawLen = song.getLength().trim();
            String formattedTime;
            if (rawLen.matches("\\d{1,2}:\\d{2}")) {
                String[] parts = rawLen.split(":");
                formattedTime = String.format("00:%02d:%02d", Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
            } else {
                formattedTime = rawLen;
            }

            prep.setString(2, formattedTime);
            prep.setString(3, song.getGenre());
            prep.setInt(4, song.getAlbumId());

            return prep.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("\n[!] MySQL Insert Error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Song song) {
        String query = "UPDATE songs SET title = ?, length = ?, genre = ?, album_id = ? WHERE id = ?";
        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setString(1, song.getTitle());
            prep.setString(2, song.getLength());
            prep.setString(3, song.getGenre());
            prep.setInt(4, song.getAlbumId());
            prep.setInt(5, song.getId());

            return prep.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[!] Error updating song: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean archive(int id) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        String query = "DELETE FROM songs WHERE id = ?";
        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, id);
            return prep.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[!] Error deleting song: " + e.getMessage());
            return false;
        }
    }
}