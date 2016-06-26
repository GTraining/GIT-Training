package com.example.kyler.musicplayer.Presenter;

import com.example.kyler.musicplayer.Model.Song;

/**
 * Created by kyler on 23/06/2016.
 */
public interface IListSongAdapterPresenter {
    void setFavoriteSong(Song song);
    void unFavoriteSong(Song song);
    boolean isFavoriteSong(Song song);
}
