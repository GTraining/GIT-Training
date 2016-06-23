package com.example.kyler.musicplayer.Presenter;

import android.content.Context;

import com.example.kyler.musicplayer.Model.Database.DatabaseHandler;
import com.example.kyler.musicplayer.Model.Song;
import com.example.kyler.musicplayer.View.Fragment.IListSongAdapter;

import java.util.ArrayList;

/**
 * Created by kyler on 23/06/2016.
 */
public class ListSongAdapterPresenter implements IListSongAdapterPresenter {
    Context context;
    IListSongAdapter adapter;
    DatabaseHandler myDB;
    ArrayList<Song> favoriteSongs;

    public ListSongAdapterPresenter(Context context, IListSongAdapter adapter) {
        this.context = context;
        this.adapter = adapter;
        myDB = new DatabaseHandler(context);
    }

    @Override
    public void setFavoriteSong(Song song) {
        myDB.addSong(song);
    }

    @Override
    public void unFavoriteSong(Song song) {
        myDB.deteleSong(song);
    }

    @Override
    public boolean isFavoriteSong(Song song) {
        favoriteSongs = myDB.getSongs();
        for(int i=0;i<favoriteSongs.size();i++){
            if(song.getSongPath().equals(favoriteSongs.get(i).getSongPath())){
                return true;
            }
        }
        return false;
    }
}
