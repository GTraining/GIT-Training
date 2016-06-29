package com.example.kyler.musicplayer.View;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.kyler.musicplayer.MyApplication;
import com.example.kyler.musicplayer.R;
import com.example.kyler.musicplayer.View.Fragment.ListAlbumFragment;
import com.example.kyler.musicplayer.View.Fragment.ListFavoriteSongFragment;
import com.example.kyler.musicplayer.View.Fragment.ListSongFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    static boolean active = false;
    Button mButtonListSong,mButtonFavoriteSong,mButtonFilter;
    final int LISTSONGID = 0, FAVORITESONGID = 1, FILTER = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButtonListSong = (Button) findViewById(R.id.activity_main_bt_list_song);
        mButtonFavoriteSong = (Button) findViewById(R.id.activity_main_bt_favorite_song);
        mButtonFilter = (Button) findViewById(R.id.activity_main_bt_filter);
        mButtonListSong.setOnClickListener(this);
        mButtonFavoriteSong.setOnClickListener(this);
        mButtonFilter.setOnClickListener(this);
        openFragment(LISTSONGID);
    }

    @Override
    protected void onResume() {
        MyApplication.getInstance().trackScreenView("Main Activity");
        super.onResume();
    }

    @Override
    protected void onStart() {
        active = true;
        super.onStart();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.activity_main_bt_list_song:
                MyApplication.getInstance().trackEvent("Menu", "List Song", "Show all songs from SD into List song Fragment");
                openFragment(LISTSONGID);
                break;
            case R.id.activity_main_bt_favorite_song:
                MyApplication.getInstance().trackEvent("Menu", "List Favorite Song", "Show all favorite songs into List song Fragment");
                openFragment(FAVORITESONGID);
                break;
            case R.id.activity_main_bt_filter:
                MyApplication.getInstance().trackEvent("Menu", "List Album Song", "Show all albums from SD into List song Fragment");
                openFragment(FILTER);
                break;
        }
    }

    public void openFragment(int id){
        Fragment fragment = null;
        switch (id){
            case LISTSONGID:
                fragment = new ListSongFragment();
                break;
            case FAVORITESONGID:
                fragment = new ListFavoriteSongFragment();
                break;
            case FILTER:
                fragment = new ListAlbumFragment();
                break;
            default : break;
        }
        if(fragment != null){
            FragmentManager fragmentManager = this.getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.main_frame,fragment).commit();
        } else{
            Log.e("Applicant MainActivity", "Error in creating fragment");
        }
    }

    @Override
    protected void onDestroy() {
        active = false;
        super.onDestroy();
    }


}
