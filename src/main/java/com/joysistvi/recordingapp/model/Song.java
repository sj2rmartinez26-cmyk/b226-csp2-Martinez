package com.joysistvi.recordingapp.model;

public class Song {
    private int id;
    private String title;
    private String length;
    private String genre;
    private int albumId;
    private int isArchived; // 0 = Active, 1 = Archived

    public Song(int id, String title, String length, String genre, int albumId, int isArchived) {
        this.id = id;
        this.title = title;
        this.length = length;
        this.genre = genre;
        this.albumId = albumId;
        this.isArchived = isArchived;
    }

    // Overloaded Constructor for Creation (defaults isArchived to 0)
    public Song(int id, String title, String length, String genre, int albumId) {
        this(id, title, length, genre, albumId, 0);
    }

    // Getters and Setters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getLength() { return length; }
    public String getGenre() { return genre; }
    public int getAlbumId() { return albumId; }
    public int getIsArchived() { return isArchived; }

    public void setId(int id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setLength(String length) { this.length = length; }
    public void setGenre(String genre) { this.genre = genre; }
    public void setAlbumId(int albumId) { this.albumId = albumId; }
    public void setIsArchived(int isArchived) { this.isArchived = isArchived; }
}