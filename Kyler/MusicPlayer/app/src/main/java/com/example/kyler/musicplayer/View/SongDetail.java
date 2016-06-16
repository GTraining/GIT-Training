package com.example.kyler.musicplayer.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.kyler.musicplayer.R;
import com.example.kyler.musicplayer.Utils.Helper;

public class SongDetail extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    ImageView timer, shuffle, repeat, back, backward, play, forward, next;
    TextView currentTxt, durationTxt, title, artist, album, author;
    SeekBar seekBar;

    boolean timerStatus = false, playStatus = false, shuffleStatus = false;
    int repeatStatus = 0;
    long currentTime = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_detail);
        init();
    }

    /**
     * setup view
     */
    private void init(){
        timer = (ImageView) findViewById(R.id.activity_song_detail_timer);
        shuffle = (ImageView) findViewById(R.id.activity_song_detail_shuffle);
        repeat = (ImageView) findViewById(R.id.activity_song_detail_repeat);
        back = (ImageView) findViewById(R.id.activity_song_detail_back);
        backward = (ImageView) findViewById(R.id.activity_song_detail_backward);
        play = (ImageView) findViewById(R.id.activity_song_detail_play);
        forward = (ImageView) findViewById(R.id.activity_song_detail_forward);
        next = (ImageView) findViewById(R.id.activity_song_detail_next);
        currentTxt = (TextView) findViewById(R.id.activity_song_detail_current_txt);
        durationTxt = (TextView) findViewById(R.id.activity_song_detail_duration_txt);
        title = (TextView) findViewById(R.id.activity_song_detail_title_txt);
        artist = (TextView) findViewById(R.id.activity_song_detail_artist_txt);
        album = (TextView) findViewById(R.id.activity_song_detail_album_txt);
        author = (TextView) findViewById(R.id.activity_song_detail_author_txt);
        seekBar = (SeekBar) findViewById(R.id.activity_song_detail_seek);
        seekBar.setMax(186547);
        seekBar.setProgress(22);
        durationTxt.setText(Helper.millisecondsToTimer(186547));

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

    /**
     * getBundle receiver
     */
    private void getBundle(){
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("abc");
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
                currentTime = currentTime - 5000;
                seekBar.setProgress((int) currentTime);
                currentTxt.setText(Helper.millisecondsToTimer(currentTime));
                break;
            case R.id.activity_song_detail_play:
                playStatus = !playStatus;
                setStatus();
                break;
            case R.id.activity_song_detail_forward:
                break;
            case R.id.activity_song_detail_next:
                break;
            case R.id.activity_song_detail_seek:
                break;
        }
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

    }
}
