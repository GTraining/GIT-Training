package com.example.kyler.musicplayer.Presenter;

import android.content.Context;

import com.example.kyler.musicplayer.Model.Database.DatabaseHandler;
import com.example.kyler.musicplayer.Model.Song;
import com.example.kyler.musicplayer.View.Fragment.IListSongView;

import java.util.ArrayList;

/**
 * Created by kyler on 23/06/2016.
 */
public class ListFavoriteSongPresenter implements IListSongPresenter{

    private Context context;
    private IListSongView view;
    private DatabaseHandler myDB;

    public ListFavoriteSongPresenter(Context context, IListSongView view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void getSong() {
        view.loadSong(getSongs());
        myDB.close();
    }

    @Override
    public void playSong(String path) {

    }

    private ArrayList<Song> getSongs(){
        myDB = new DatabaseHandler(context);
        return myDB.getSongs();
    }
}
