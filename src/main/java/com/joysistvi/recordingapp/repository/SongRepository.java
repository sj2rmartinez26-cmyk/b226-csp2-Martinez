package com.joysistvi.recordingapp.repository;

import com.joysistvi.recordingapp.model.Song;
import java.util.List;

public interface SongRepository {
    List<Song> findAll();
    Song findById(int id);
    boolean create(Song song);
    boolean update(Song song);
    boolean archive(int id);
    boolean delete(int id);
}