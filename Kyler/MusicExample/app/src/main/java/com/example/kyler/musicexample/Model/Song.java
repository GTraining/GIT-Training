package com.example.kyler.musicexample.Model;

/**
 * Created by kyler on 14/06/2016.
 */
public class Song {
    private int id;
    private String title;
    private String artist;
    private String author;
    private String album;
    private int trackLength;

    public Song(int id, String title, String artist, String author, String album, int trackLength) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.author = author;
        this.album = album;
        this.trackLength = trackLength;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public int getTrackLength() {
        return trackLength;
    }

    public void setTrackLength(int trackLength) {
        this.trackLength = trackLength;
    }
}
