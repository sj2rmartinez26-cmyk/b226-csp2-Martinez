package com.joysistvi.recordingapp.repository;

public interface SongRepository {
    public void readSong();
    public void readArchivedSong();
    public void createSong(String title, String length, String genre, int album_id);
    public void deleteSong(int id);

}
