package com.example.kyler.musicexample.View;

import android.content.ContentResolver;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.kyler.musicexample.Presenter.MenuPresenter;
import com.example.kyler.musicexample.R;

public class MenuActivity extends AppCompatActivity implements IMenuView {
    MenuPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        presenter = new MenuPresenter(getApplicationContext(), this);
    }

    @Override
    public void loadSong() {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }
}
