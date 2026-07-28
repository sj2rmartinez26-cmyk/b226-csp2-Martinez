package com.joysistvi.recordingapp.repository;

import com.joysistvi.recordingapp.model.Album;
import java.util.List;

public interface AlbumRepository {
    List<Album> findAll();
    Album findById(int id);
    boolean create(Album album);
    boolean update(Album album);
    boolean delete(int id);
}