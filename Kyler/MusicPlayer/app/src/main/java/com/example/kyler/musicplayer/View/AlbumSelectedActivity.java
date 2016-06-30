package com.example.kyler.musicplayer.View;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.kyler.musicplayer.AnalyticsTrackers;
import com.example.kyler.musicplayer.HockeyAppTracking;
import com.example.kyler.musicplayer.Model.Song;
import com.example.kyler.musicplayer.MyApplication;
import com.example.kyler.musicplayer.Presenter.IListSongPresenter;
import com.example.kyler.musicplayer.Presenter.ListSongPresenter;
import com.example.kyler.musicplayer.R;
import com.example.kyler.musicplayer.View.Fragment.IListSongView;
import com.example.kyler.musicplayer.View.Fragment.ListSongAdapter;

import java.util.ArrayList;

public class AlbumSelectedActivity extends AppCompatActivity implements IListSongView,AdapterView.OnItemClickListener{
    ListView selectedAlbum;
    ListSongAdapter adapter;
    IListSongPresenter presenter;
    String album = "";
    ArrayList<Song> songs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HockeyAppTracking.checkForUpdates(this);
        setContentView(R.layout.activity_album_selected);
        album = getIntent().getStringExtra(String.valueOf(R.string.albumSelected));
        setUpToolbar();
        selectedAlbum = (ListView) findViewById(R.id.album_selected_listview);
        selectedAlbum.setOnItemClickListener(this);
        presenter = new ListSongPresenter(this,this);
        presenter.getSong(album);
    }

    @Override
    protected void onResume() {
        HockeyAppTracking.checkForCrashes(this);
        super.onResume();
    }

    /**
     * set up toolbar with name of album
     */
    private void setUpToolbar(){
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitleTextColor(Color.WHITE);
        myToolbar.setNavigationIcon(R.drawable.navigationbackicon);
        setSupportActionBar(myToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(album);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        MyApplication.getInstance().trackEvent(AnalyticsTrackers.CHOOSESONG_CATEGORY, "Songs from album selected", "Choose song to play");
        Intent intent = new Intent(this, SongDetailActivity.class);
        ArrayList<String> arrSongPaths = new ArrayList<>();
        for(int j=0;j<songs.size();j++){
            arrSongPaths.add(songs.get(j).getSongPath());
        }
        intent.putStringArrayListExtra(String.valueOf(R.string.path),arrSongPaths);
        intent.putExtra(String.valueOf(R.string.currentID),i);
        startActivity(intent);
    }

    @Override
    public void loadSong(ArrayList<Song> songs) {
        this.songs = songs;
        adapter = new ListSongAdapter(this,songs,false);
        selectedAlbum.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
            default: super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        HockeyAppTracking.unregisterManagers(this);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        HockeyAppTracking.unregisterManagers(this);
        super.onDestroy();
    }
}
