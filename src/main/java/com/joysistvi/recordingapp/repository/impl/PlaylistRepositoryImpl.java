package com.joysistvi.recordingapp.repository.impl;

import com.joysistvi.recordingapp.config.DbConnection;
import com.joysistvi.recordingapp.model.Playlist;
import com.joysistvi.recordingapp.repository.PlaylistRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaylistRepositoryImpl implements PlaylistRepository {
    private final DbConnection dbConnection;

    public PlaylistRepositoryImpl(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<Playlist> findAll() {
        List<Playlist> playlists = new ArrayList<>();
        String query = "SELECT * FROM playlists";
        try (Connection conn = dbConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                playlists.add(new Playlist(rs.getInt("id"), rs.getString("name"), rs.getInt("user_id")));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching playlists: " + e.getMessage());
        }
        return playlists;
    }

    @Override
    public Playlist findById(int id) {
        String query = "SELECT * FROM playlists WHERE id = ?";
        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {
            prep.setInt(1, id);
            ResultSet rs = prep.executeQuery();
            if (rs.next()) {
                return new Playlist(rs.getInt("id"), rs.getString("name"), rs.getInt("user_id"));
            }
        } catch (SQLException e) {
            System.err.println("Error finding playlist: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean create(Playlist playlist) {
        String query = "INSERT INTO playlists (name, user_id) VALUES (?, ?)";
        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {
            prep.setString(1, playlist.getName());
            prep.setInt(2, playlist.getUserId());
            return prep.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error creating playlist: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Playlist playlist) {
        String query = "UPDATE playlists SET name = ?, user_id = ? WHERE id = ?";
        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {
            prep.setString(1, playlist.getName());
            prep.setInt(2, playlist.getUserId());
            prep.setInt(3, playlist.getId());
            return prep.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating playlist: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String query = "DELETE FROM playlists WHERE id = ?";
        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {
            prep.setInt(1, id);
            return prep.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting playlist: " + e.getMessage());
            return false;
        }
    }
}