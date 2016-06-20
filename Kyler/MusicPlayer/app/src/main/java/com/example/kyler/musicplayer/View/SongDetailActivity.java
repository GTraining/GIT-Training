package com.example.kyler.musicplayer.View;

import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.kyler.musicplayer.Model.MyBindService;
import com.example.kyler.musicplayer.Model.Song;
import com.example.kyler.musicplayer.Presenter.ISongDetailPresenter;
import com.example.kyler.musicplayer.Presenter.SongDetailPresenter;
import com.example.kyler.musicplayer.R;
import com.example.kyler.musicplayer.Utils.Helper;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class SongDetailActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, ISongDetailView{
    ImageButton timer, shuffle, repeat, previous, backward, play ,forward, next;
    TextView currentTxt, durationTxt, title, artist, album, author;
    SeekBar seekBar;
    ImageView image;
    LinearLayout background;

    boolean timerStatus = false, playStatus = false, shuffleStatus = false;
    int repeatStatus = 0;
    int currentID;
    long currentTime = 0;
    boolean seekbarChanging = false;
    Song song;
    ISongDetailPresenter detailPresenter;
    ArrayList<String> arrSongPaths;
    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_detail);
        init();
        Intent intent = getIntent();
        arrSongPaths = intent.getStringArrayListExtra(String.valueOf(R.string.path));
        currentID = intent.getIntExtra(String.valueOf(R.string.currentID),0);
        detailPresenter = new SongDetailPresenter(getApplicationContext(),this);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(detailPresenter.isPlaying()){
                    currentTime = detailPresenter.getCurrent();
                    detailPresenter.getSong();
                }else {
                    detailPresenter.setSongs(arrSongPaths, currentID);
                }
                playSong();
                setStatus();
            }
        }, 500);
    }

    /**
     * setup view
     */
    private void init(){
        mHandler = new Handler();
        timer = (ImageButton) findViewById(R.id.activity_song_detail_timer);
        shuffle = (ImageButton) findViewById(R.id.activity_song_detail_shuffle);
        repeat = (ImageButton) findViewById(R.id.activity_song_detail_repeat);
        previous = (ImageButton) findViewById(R.id.activity_song_detail_previous);
        backward = (ImageButton) findViewById(R.id.activity_song_detail_backward);
        play = (ImageButton) findViewById(R.id.activity_song_detail_play);
        forward = (ImageButton) findViewById(R.id.activity_song_detail_forward);
        next = (ImageButton) findViewById(R.id.activity_song_detail_next);
        currentTxt = (TextView) findViewById(R.id.activity_song_detail_current_txt);
        durationTxt = (TextView) findViewById(R.id.activity_song_detail_duration_txt);
        image = (ImageView) findViewById(R.id.activity_song_detail_image);
        title = (TextView) findViewById(R.id.activity_song_detail_title_txt);
        artist = (TextView) findViewById(R.id.activity_song_detail_artist_txt);
        album = (TextView) findViewById(R.id.activity_song_detail_album_txt);
        author = (TextView) findViewById(R.id.activity_song_detail_author_txt);
        seekBar = (SeekBar) findViewById(R.id.activity_song_detail_seek);
        background = (LinearLayout) findViewById(R.id.activity_song_detail_background);
        seekBar.setProgress(0);

        timer.setOnClickListener(this);
        shuffle.setOnClickListener(this);
        repeat.setOnClickListener(this);
        previous.setOnClickListener(this);
        backward.setOnClickListener(this);
        play.setOnClickListener(this);
        forward.setOnClickListener(this);
        next.setOnClickListener(this);
        seekBar.setOnSeekBarChangeListener(this);
    }

    /**
     * change the status of button for events click on it.
     */
    private void setStatus(){
        if(!playStatus){
            play.setImageResource(R.drawable.playbutton);
        }else{
            play.setImageResource(R.drawable.pausebutton);
        }

        if(shuffleStatus){
            shuffle.setImageResource(R.drawable.shufflebutton);
        }else{
            shuffle.setImageResource(R.drawable.shuffledisablebutton);
        }

        switch (repeatStatus){
            case 0:
                repeat.setImageResource(R.drawable.repeatbutton);
                break;
            case 1:
                repeat.setImageResource(R.drawable.repeatonebutton);
                break;
            case 2:
                repeat.setImageResource(R.drawable.repeatdisablebutton);
                break;
            default:break;
        }
    }

    /**
     * change the play status to decide the status of playing song.
     */
    private void playSong(){
        playStatus = !playStatus;
        if(playStatus){
            detailPresenter.resumeSong();
            updateSeekbar();
        }
        else {
            detailPresenter.pauseSong();
        }
    }

    /**
     * seek the music to the time put in
     * @param time
     */
    private void seekTo(long time){
        detailPresenter.seekTo(time);
        seekBar.setProgress((int) time);
        currentTxt.setText(Helper.millisecondsToTimer(time));
    }

    /**
     * play the next song
     */
    private void nextSong(){
        playStatus = true;
        detailPresenter.playNext();
        currentTime = 0;
        seekBar.setProgress(0);
    }

    /**
     * play previous song
     */
    private void previousSong(){
        playStatus = true;
        detailPresenter.playPrevious();
        currentTime = 0;
        seekBar.setProgress(0);
    }

    /**
     * update the current time on seekbar
     */
    @Override
    public void updateSeekbar(){
        mHandler.post(mUpdateSeekbarRunnable);
    }

    /**
     * runnable of update seekbar
     */
    private Runnable mUpdateSeekbarRunnable = new Runnable() {
        @Override
        public void run() {
            currentTime = detailPresenter.getCurrent();
            seekBar.setProgress((int) currentTime);
            currentTxt.setText(Helper.millisecondsToTimer(currentTime));
            mHandler.postDelayed(this,100);
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_song_detail_timer:
                break;
            case R.id.activity_song_detail_shuffle:
                shuffleStatus = !shuffleStatus;
                detailPresenter.setShuffle(shuffleStatus);
                setStatus();
                break;
            case R.id.activity_song_detail_repeat:
                repeatStatus++;
                if(repeatStatus>2) repeatStatus=0;
                setStatus();
                break;
            case R.id.activity_song_detail_previous:
                previousSong();
                break;
            case R.id.activity_song_detail_backward:
                if(currentTime - 5000 < 0)
                    currentTime = 0;
                else
                    currentTime = currentTime - 5000;
                seekTo(currentTime);
                break;
            case R.id.activity_song_detail_play:
                playSong();
                break;
            case R.id.activity_song_detail_forward:
                if(currentTime + 5000 > song.getSongDuration())
                    currentTime = song.getSongDuration();
                else
                    currentTime = currentTime + 5000;
                seekTo(currentTime);
                break;
            case R.id.activity_song_detail_next:
                nextSong();
                break;
            case R.id.activity_song_detail_seek:
                break;
        }
        setStatus();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        if(currentTime == song.getSongDuration()){
            nextSong();
        }else {
            currentTime = seekBar.getProgress();
            currentTxt.setText(Helper.millisecondsToTimer(currentTime));
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if(currentTime == song.getSongDuration()){
            nextSong();
        }else {
            seekTo(currentTime);
        }
        updateSeekbar();
    }

    @Override
    public void loadSong(Song song) {
        this.song = song;
        seekBar.setMax((int) song.getSongDuration());
        byte[] img = song.getSongImage();
        if(img != null){
            InputStream is = new ByteArrayInputStream(img);
            Bitmap bm = BitmapFactory.decodeStream(is);
            image.setImageBitmap(bm);
        }else{
            image.setImageResource(R.drawable.defaultpic);
        }
        title.setText(song.getSongTitle());
        artist.setText(song.getSongArtist());
        album.setText(song.getSongAlbum());
        author.setText(song.getSongAuthor());
        durationTxt.setText(Helper.millisecondsToTimer(song.getSongDuration()));
    }

    @Override
    public void updateBackground(int resource) {
        background.setBackgroundResource(resource);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
