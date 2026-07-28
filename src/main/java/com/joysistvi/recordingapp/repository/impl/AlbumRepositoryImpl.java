package com.joysistvi.recordingapp.repository.impl;

import com.joysistvi.recordingapp.config.DbConnection;
import com.joysistvi.recordingapp.model.Album;
import com.joysistvi.recordingapp.repository.AlbumRepository;
import com.joysistvi.recordingapp.repository.ArtistRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlbumRepositoryImpl implements AlbumRepository {
    private final DbConnection dbConnection;

    public AlbumRepositoryImpl(DbConnection dbConnection, ArtistRepository artistRepo) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<Album> findAll() {
        List<Album> albums = new ArrayList<>();
        String query = "SELECT * FROM albums";
        try (Connection conn = dbConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                albums.add(new Album(rs.getInt("id"), rs.getString("title"), rs.getInt("release_year"), rs.getInt("artist_id")));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching albums: " + e.getMessage());
        }
        return albums;
    }

    @Override
    public Album findById(int id) {
        String query = "SELECT * FROM albums WHERE id = ?";
        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {
            prep.setInt(1, id);
            ResultSet rs = prep.executeQuery();
            if (rs.next()) {
                return new Album(rs.getInt("id"), rs.getString("title"), rs.getInt("release_year"), rs.getInt("artist_id"));
            }
        } catch (SQLException e) {
            System.err.println("Error finding album: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean create(Album album) {
        String query = "INSERT INTO albums (title, release_year, artist_id) VALUES (?, ?, ?)";
        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {
            prep.setString(1, album.getTitle());
            prep.setInt(2, album.getReleaseYear());
            prep.setInt(3, album.getArtistId());
            return prep.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error creating album: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Album album) {
        String query = "UPDATE albums SET title = ?, release_year = ?, artist_id = ? WHERE id = ?";
        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {
            prep.setString(1, album.getTitle());
            prep.setInt(2, album.getReleaseYear());
            prep.setInt(3, album.getArtistId());
            prep.setInt(4, album.getId());
            return prep.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating album: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String query = "DELETE FROM albums WHERE id = ?";
        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {
            prep.setInt(1, id);
            return prep.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting album: " + e.getMessage());
            return false;
        }
    }
}