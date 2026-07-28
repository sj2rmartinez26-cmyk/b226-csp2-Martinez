package com.joysistvi.recordingapp.controller;

import com.joysistvi.recordingapp.model.Artist;
import com.joysistvi.recordingapp.service.ArtistService;
import java.util.List;

public class ArtistController {
    private final ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    public List<Artist> getAll() { return artistService.getAllArtists(); }
    public boolean create(String name, String genre) { return artistService.addArtist(name, genre); }
    public boolean update(int id, String name, String genre) { return artistService.updateArtist(id, name, genre); }
    public boolean delete(int id) { return artistService.deleteArtist(id); }
}