package com.joysistvi.recordingapp.service;

import com.joysistvi.recordingapp.model.Artist;
import com.joysistvi.recordingapp.repository.ArtistRepository;
import java.util.List;

public class ArtistService {
    private final ArtistRepository artistRepository;

    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public List<Artist> getAllArtists() { return artistRepository.findAll(); }

    public boolean addArtist(String name, String genre) {
        if (name == null || name.isBlank()) return false;
        return artistRepository.create(new Artist(0, name, genre));
    }

    public boolean updateArtist(int id, String name, String genre) {
        if (artistRepository.findById(id) == null) return false;
        return artistRepository.update(new Artist(id, name, genre));
    }

    public boolean deleteArtist(int id) { return artistRepository.delete(id); }
}