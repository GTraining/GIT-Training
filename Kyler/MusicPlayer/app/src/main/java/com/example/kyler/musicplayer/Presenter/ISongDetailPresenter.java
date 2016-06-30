package com.example.kyler.musicplayer.Presenter;


import java.util.ArrayList;

/**
 * Created by kyler on 16/06/2016.
 */
public interface ISongDetailPresenter {
    void setSongs(ArrayList<String> arrStringPaths, int currentID);
    void setOnPlayingSongs(ArrayList<String> arrStringPaths, int currentID);
    void resumeSong();
    void getSong();
    void pauseSong();
    void seekTo(long time);
    void setShuffle(boolean shuffle);
    void playNext();
    void playPrevious();
    void setTimer(int minute);
    void setRepeat(int repeat);
    void setShake(boolean shake);
    boolean isPlaying();
    boolean getTimerComplete();
    int getTimerTime();
    long getCurrent();
    String getCurrentPath();
    int getRepeatStatus();
    boolean getShakeStatus();
    boolean getShuffleStatus();
    void onFinish();
    boolean isBinding();
}
