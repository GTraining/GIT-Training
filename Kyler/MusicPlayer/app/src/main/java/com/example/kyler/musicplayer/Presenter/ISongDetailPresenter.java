package com.example.kyler.musicplayer.Presenter;

import com.example.kyler.musicplayer.Model.Song;

import java.util.ArrayList;

/**
 * Created by kyler on 16/06/2016.
 */
public interface ISongDetailPresenter {
    void setSongs(ArrayList<String> arrStringPaths, int currentID);
    void resumeSong();
    void getSong();
    void pauseSong();
    void seekTo(long time);
    void setShuffle(boolean shuffle);
    void playNext();
    void playPrevious();
    void setTimer(int minute);
    boolean isPlaying();
    int getTimerTime();
    long getCurrent();
}
