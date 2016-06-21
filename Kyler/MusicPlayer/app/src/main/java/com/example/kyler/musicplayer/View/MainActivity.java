package com.example.kyler.musicplayer.View;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.NotificationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.kyler.musicplayer.R;
import com.example.kyler.musicplayer.View.Fragment.ListSongFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button mButtonListSong,mButtonFavoriteSong,mButtonFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButtonListSong = (Button) findViewById(R.id.activity_main_bt_list_song);
        mButtonFavoriteSong = (Button) findViewById(R.id.activity_main_bt_favorite_song);
        mButtonFilter = (Button) findViewById(R.id.activity_main_bt_filter);
        mButtonListSong.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.activity_main_bt_list_song:
                Fragment fragment = new ListSongFragment();
                openFragment(fragment);
                break;
            case R.id.activity_main_bt_favorite_song:
                break;
            case R.id.activity_main_bt_filter:
                break;
        }
    }

    public void openFragment(android.app.Fragment fragment){
        if(fragment != null){
            FragmentManager fragmentManager = this.getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.main_frame,fragment).commit();
        } else{
            Log.e("Applicant MainActivity", "Error in creating fragment");
        }
    }

    @Override
    protected void onDestroy() {
        ((NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE)).cancelAll();
        super.onDestroy();
    }
}
