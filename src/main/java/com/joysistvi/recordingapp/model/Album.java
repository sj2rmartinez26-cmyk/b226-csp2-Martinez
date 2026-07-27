package com.joysistvi.recordingapp.model;

public class Album {
    private int id;
    private String title;
    private int releaseYear;
    private int artistId;

    public Album() {}

    public Album(int id, String title, int releaseYear, int artistId) {
        this.id = id;
        this.title = title;
        this.releaseYear = releaseYear;
        this.artistId = artistId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getReleaseYear() { return releaseYear; }
    public void setReleaseYear(int releaseYear) { this.releaseYear = releaseYear; }

    public int getArtistId() { return artistId; }
    public void setArtistId(int artistId) { this.artistId = artistId; }
}