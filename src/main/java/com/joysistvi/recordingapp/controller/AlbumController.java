package com.joysistvi.recordingapp.controller;

import com.joysistvi.recordingapp.model.Album;
import com.joysistvi.recordingapp.service.AlbumService;
import java.util.List;

public class AlbumController {
    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    public List<Album> getAll() { return albumService.getAllAlbums(); }
    public boolean create(String title, int year, int artistId) { return albumService.addAlbum(title, year, artistId); }
    public boolean update(int id, String title, int year, int artistId) { return albumService.updateAlbum(id, title, year, artistId); }
    public boolean delete(int id) { return albumService.deleteAlbum(id); }
}