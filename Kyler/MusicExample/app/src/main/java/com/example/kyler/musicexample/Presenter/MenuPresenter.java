package com.example.kyler.musicexample.Presenter;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.kyler.musicexample.Model.Song;
import com.example.kyler.musicexample.View.IMenuView;

import java.util.ArrayList;

/**
 * Created by kyler on 14/06/2016.
 */
public class MenuPresenter implements IMenuPresenter{
    Context context;
    IMenuView menuView;

    public MenuPresenter(Context context, IMenuView menuView) {
        this.context = context;
        this.menuView = menuView;
    }

    /**
     * create the Song array to show up to View
     * @return
     */
    @Override
    public ArrayList<Song> getSongList() {
        ArrayList<Song> songs = null;
        return songs;
    }
}
