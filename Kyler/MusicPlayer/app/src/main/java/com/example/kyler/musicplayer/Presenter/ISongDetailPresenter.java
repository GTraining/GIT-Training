package com.example.kyler.musicplayer.Presenter;

import com.example.kyler.musicplayer.Model.Song;

/**
 * Created by kyler on 16/06/2016.
 */
public interface ISongDetailPresenter {
    void getSong(String path);
    void playSong(Song song);
    void stopSong();
    void seekTo(long time);
}
