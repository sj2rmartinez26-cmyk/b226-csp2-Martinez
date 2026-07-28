package com.joysistvi.recordingapp.repository.impl;

import com.joysistvi.recordingapp.config.DbConnection;
import com.joysistvi.recordingapp.model.Artist;
import com.joysistvi.recordingapp.repository.ArtistRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArtistRepositoryImpl implements ArtistRepository {
    private final DbConnection dbConnection;

    public ArtistRepositoryImpl(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<Artist> findAll() {
        List<Artist> artists = new ArrayList<>();
        String query = "SELECT * FROM artists";
        try (Connection conn = dbConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                artists.add(new Artist(rs.getInt("id"), rs.getString("name"), rs.getString("genre")));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching artists: " + e.getMessage());
        }
        return artists;
    }

    @Override
    public Artist findById(int id) {
        String query = "SELECT * FROM artists WHERE id = ?";
        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {
            prep.setInt(1, id);
            ResultSet rs = prep.executeQuery();
            if (rs.next()) {
                return new Artist(rs.getInt("id"), rs.getString("name"), rs.getString("genre"));
            }
        } catch (SQLException e) {
            System.err.println("Error finding artist: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean create(Artist artist) {
        String query = "INSERT INTO artists (name, genre) VALUES (?, ?)";
        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {
            prep.setString(1, artist.getName());
            prep.setString(2, artist.getGenre());
            return prep.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error creating artist: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Artist artist) {
        String query = "UPDATE artists SET name = ?, genre = ? WHERE id = ?";
        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {
            prep.setString(1, artist.getName());
            prep.setString(2, artist.getGenre());
            prep.setInt(3, artist.getId());
            return prep.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating artist: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String query = "DELETE FROM artists WHERE id = ?";
        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {
            prep.setInt(1, id);
            return prep.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting artist: " + e.getMessage());
            return false;
        }
    }
}