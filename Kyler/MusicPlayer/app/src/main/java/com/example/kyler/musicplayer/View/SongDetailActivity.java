package com.example.kyler.musicplayer.View;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.kyler.musicplayer.Model.Song;
import com.example.kyler.musicplayer.Presenter.ISongDetailPresenter;
import com.example.kyler.musicplayer.Presenter.SongDetailPresenter;
import com.example.kyler.musicplayer.R;
import com.example.kyler.musicplayer.Utils.Helper;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class SongDetailActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, ISongDetailView{
    ImageButton timer, shuffle, repeat, back, backward, play ,forward, next;
    TextView currentTxt, durationTxt, title, artist, album, author;
    SeekBar seekBar;
    ImageView image;

    boolean timerStatus = false, playStatus = false, shuffleStatus = false;
    int repeatStatus = 0;
    long currentTime = 0;
    Song song;
    ISongDetailPresenter detailPresenter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_detail);
        init();
        String path = getIntent().getStringExtra(String.valueOf(R.string.path));
        detailPresenter = new SongDetailPresenter(getApplicationContext(),this);
        detailPresenter.getSong(path);
        seekBar.setMax((int) song.getSongDuration());
    }

    /**
     * setup view
     */
    private void init(){
        timer = (ImageButton) findViewById(R.id.activity_song_detail_timer);
        shuffle = (ImageButton) findViewById(R.id.activity_song_detail_shuffle);
        repeat = (ImageButton) findViewById(R.id.activity_song_detail_repeat);
        back = (ImageButton) findViewById(R.id.activity_song_detail_back);
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
        seekBar.setProgress(0);

        timer.setOnClickListener(this);
        shuffle.setOnClickListener(this);
        repeat.setOnClickListener(this);
        back.setOnClickListener(this);
        backward.setOnClickListener(this);
        play.setOnClickListener(this);
        forward.setOnClickListener(this);
        next.setOnClickListener(this);
        seekBar.setOnSeekBarChangeListener(this);
    }

    private void setStatus(){
        if(!playStatus){
            play.setImageResource(R.drawable.playbutton);
        }else{
            play.setImageResource(R.drawable.pausebutton);
        }

        if(!shuffleStatus){
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

    private void playSong(){
        playStatus = !playStatus;
        if(playStatus)
            detailPresenter.playSong(song);
        else
            detailPresenter.stopSong();
    }

    private void seekTo(long time){
        detailPresenter.seekTo(time);
        seekBar.setProgress((int) time);
        currentTxt.setText(Helper.millisecondsToTimer(time));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_song_detail_timer:
                break;
            case R.id.activity_song_detail_shuffle:
                shuffleStatus = !shuffleStatus;
                setStatus();
                break;
            case R.id.activity_song_detail_repeat:
                repeatStatus++;
                if(repeatStatus>2) repeatStatus=0;
                setStatus();
                break;
            case R.id.activity_song_detail_back:
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
                break;
            case R.id.activity_song_detail_seek:
                break;
        }
        setStatus();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        currentTime = seekBar.getProgress();
        currentTxt.setText(Helper.millisecondsToTimer(currentTime));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        seekTo(currentTime);
    }

    @Override
    public void loadSong(Song song) {
        this.song = song;
        byte[] img = song.getSongImage();
        if(img != null){
            InputStream is = new ByteArrayInputStream(img);
            Bitmap bm = BitmapFactory.decodeStream(is);
            image.setImageBitmap(bm);
        }
        title.setText(song.getSongTitle());
        artist.setText(song.getSongArtist());
        album.setText(song.getSongAlbum());
        author.setText(song.getSongAuthor());
        durationTxt.setText(Helper.millisecondsToTimer(song.getSongDuration()));
    }

    @Override
    public void update(long time) {
        currentTime = time;
        seekBar.setProgress((int)time);
    }
}
