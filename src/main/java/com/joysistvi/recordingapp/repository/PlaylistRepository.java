package com.joysistvi.recordingapp.repository;

import com.joysistvi.recordingapp.model.Playlist;
import java.util.List;

public interface PlaylistRepository {
    List<Playlist> findAll();
    Playlist findById(int id);
    boolean create(Playlist playlist);
    boolean update(Playlist playlist);
    boolean delete(int id);
}