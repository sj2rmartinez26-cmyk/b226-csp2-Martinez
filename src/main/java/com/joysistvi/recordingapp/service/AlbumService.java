package com.joysistvi.recordingapp.service;

import com.joysistvi.recordingapp.model.Album;
import com.joysistvi.recordingapp.repository.AlbumRepository;
import com.joysistvi.recordingapp.repository.ArtistRepository;
import java.util.List;

public class AlbumService {
    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;

    public AlbumService(AlbumRepository albumRepository, ArtistRepository artistRepository) {
        this.albumRepository = albumRepository;
        this.artistRepository = artistRepository;
    }

    public List<Album> getAllAlbums() { return albumRepository.findAll(); }

    public boolean addAlbum(String title, int year, int artistId) {
        // Prevents foreign key constraint failure!
        if (artistRepository.findById(artistId) == null) {
            System.out.println("Error: Artist ID does not exist.");
            return false;
        }
        return albumRepository.create(new Album(0, title, year, artistId));
    }

    public boolean updateAlbum(int id, String title, int year, int artistId) {
        if (albumRepository.findById(id) == null || artistRepository.findById(artistId) == null) return false;
        return albumRepository.update(new Album(id, title, year, artistId));
    }

    public boolean deleteAlbum(int id) { return albumRepository.delete(id); }
}