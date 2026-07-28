package com.joysistvi.recordingapp.repository;

import com.joysistvi.recordingapp.model.Artist;
import java.util.List;

public interface ArtistRepository {
    List<Artist> findAll();
    Artist findById(int id);
    boolean create(Artist artist);
    boolean update(Artist artist);
    boolean delete(int id);
}