package com.example.kyler.musicplayer.Model;

/**
 * Created by kyler on 27/06/2016.
 */
public class Album {
    String albumName;
    private byte[] albumImage;

    public Album(String albumName, byte[] albumImage) {
        this.albumName = albumName;
        this.albumImage = albumImage;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public byte[] getAlbumImage() {
        return albumImage;
    }

    public void setAlbumImage(byte[] albumImage) {
        this.albumImage = albumImage;
    }
}
