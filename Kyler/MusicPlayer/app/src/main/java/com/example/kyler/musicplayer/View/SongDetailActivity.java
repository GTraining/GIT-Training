package com.example.kyler.musicplayer.View;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kyler.musicplayer.AnalyticsTrackers;
import com.example.kyler.musicplayer.HockeyAppTracking;
import com.example.kyler.musicplayer.Model.Song;
import com.example.kyler.musicplayer.MyApplication;
import com.example.kyler.musicplayer.Presenter.ISongDetailPresenter;
import com.example.kyler.musicplayer.Presenter.SongDetailPresenter;
import com.example.kyler.musicplayer.R;
import com.example.kyler.musicplayer.Utils.Helper;

import net.hockeyapp.android.metrics.MetricsManager;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class SongDetailActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, ISongDetailView, View.OnTouchListener{
    ImageButton timer, shuffle, repeat, shake, previous, backward, play ,forward, next;
    TextView currentTxt, durationTxt, title, artist, album, author;
    SeekBar seekBar;
    ImageView image;
    LinearLayout background;
    Dialog timerDialog;

    boolean playStatus = false, shuffleStatus = false, shakeStatus = false;
    int repeatStatus = 0, currentID, timerTime=0;
    long currentTime = 0;
    Song song;
    ISongDetailPresenter detailPresenter;
    ArrayList<String> arrSongPaths;
    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HockeyAppTracking.checkForUpdates(this);
        MetricsManager.register(this, getApplication());
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
                    repeatStatus = detailPresenter.getRepeatStatus();
                    shuffleStatus = detailPresenter.getShuffleStatus();
                    shakeStatus = detailPresenter.getShakeStatus();
                    if(arrSongPaths.get(currentID).equals(detailPresenter.getCurrentPath())) {
                        detailPresenter.getSong();
                        detailPresenter.setOnPlayingSongs(arrSongPaths, currentID);
                    }else {
                        detailPresenter.setSongs(arrSongPaths, currentID);
                    }
                }else {
                    detailPresenter.setSongs(arrSongPaths, currentID);
                }
                playSong();
                setStatus();
            }
        }, 500);
    }

    @Override
    protected void onResume() {
        MyApplication.getInstance().trackScreenView("Song Detail Activity");
        HockeyAppTracking.checkForCrashes(this);
        super.onResume();
    }

    /**
     * setup view
     */
    private void init(){
        mHandler = new Handler();
        timer = (ImageButton) findViewById(R.id.activity_song_detail_timer);
        shuffle = (ImageButton) findViewById(R.id.activity_song_detail_shuffle);
        repeat = (ImageButton) findViewById(R.id.activity_song_detail_repeat);
        shake = (ImageButton) findViewById(R.id.activity_song_detail_shake);
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
        setDialog(0);
        timer.setOnClickListener(this);
        shuffle.setOnClickListener(this);
        repeat.setOnClickListener(this);
        shake.setOnClickListener(this);
        previous.setOnClickListener(this);
        play.setOnClickListener(this);
        next.setOnClickListener(this);
        seekBar.setOnSeekBarChangeListener(this);
        backward.setOnTouchListener(this);
        forward.setOnTouchListener(this);
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

        if(shakeStatus){
            shake.setImageResource(R.drawable.shakebutton);
        }else{
            shake.setImageResource(R.drawable.shakedisablebutton);
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
     * set Dialog for Timer
     */
    private void setDialog(int time){
        timerDialog = new Dialog(this);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        timerDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        timerDialog.setContentView(R.layout.song_timer_dialog);
        timerDialog.getWindow().setLayout(width,height/6);
        timerDialog.setCancelable(true);
        SeekBar seekBar = (SeekBar) timerDialog.findViewById(R.id.timer_song_seekbar);
        final TextView timertxt = (TextView) timerDialog.findViewById(R.id.timer_song_txt);
        seekBar.setProgress(time);
        timertxt.setText("Turn off in "+time+" minutes");
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                timerTime = seekBar.getProgress();
                timertxt.setText("Turn off in "+timerTime+" minutes");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                MyApplication.getInstance().trackEvent(AnalyticsTrackers.PLAYING_CATEGORY, "Set timer", "Set time to stop music");
                MetricsManager.trackEvent("Set timer");
                detailPresenter.setTimer(timerTime);
            }
        });
    }

    /**
     * update the current time on seekbar
     */
    @Override
    public void updateSeekbar(){
        mHandler.post(mUpdateSeekbarRunnable);
    }

    @Override
    public void stopMusic() {
        finish();
    }

    /**
     * runnable of update seekbar
     */
    private Runnable mUpdateSeekbarRunnable = new Runnable() {
        @Override
        public void run() {
            if(detailPresenter.getTimerComplete()) {
                detailPresenter.onFinish();
                finish();
            }else {
                currentTime = detailPresenter.getCurrent();
                seekBar.setProgress((int) currentTime);
                currentTxt.setText(Helper.millisecondsToTimer(currentTime));
                if (!song.getSongPath().equals(detailPresenter.getCurrentPath())) {
                    detailPresenter.getSong();
                }
                if (detailPresenter.isPlaying()) {
                    playStatus = true;
                    play.setImageResource(R.drawable.pausebutton);
                } else {
                    playStatus = false;
                    play.setImageResource(R.drawable.playbutton);
                }
                mHandler.postDelayed(this, 500);
            }
        }
    };

    public void showTimerDialog(){
        if(detailPresenter.isPlaying()) {
            timerTime = detailPresenter.getTimerTime();
        }else{
            timerTime = 0;
        }
        setDialog(timerTime);
        timerDialog.show();
    }

    private void setRepeat(){
        repeatStatus++;
        if(repeatStatus>2) repeatStatus=0;
        detailPresenter.setRepeat(repeatStatus);
        setStatus();
    }

    private void setShake(){
        shakeStatus = !shakeStatus;
        detailPresenter.setShake(shakeStatus);
        if(shakeStatus){
            Toast.makeText(getApplicationContext(),"Shaking for control music : On",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(),"Shaking for control music : Off",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_song_detail_timer:
                MyApplication.getInstance().trackEvent(AnalyticsTrackers.PLAYING_CATEGORY, "Timer music", "Timer for stop music");
                MetricsManager.trackEvent("Timer music");
                showTimerDialog();
                break;
            case R.id.activity_song_detail_shuffle:
                MyApplication.getInstance().trackEvent(AnalyticsTrackers.PLAYING_CATEGORY, "Shuffle music", "Set shuffle for music");
                MetricsManager.trackEvent("Shuffle music");
                shuffleStatus = !shuffleStatus;
                detailPresenter.setShuffle(shuffleStatus);
                setStatus();
                break;
            case R.id.activity_song_detail_repeat:
                MyApplication.getInstance().trackEvent(AnalyticsTrackers.PLAYING_CATEGORY, "Repeat click", "Set repeat for music");
                MetricsManager.trackEvent("Repeat click");
                setRepeat();
                break;
            case R.id.activity_song_detail_shake:
                MyApplication.getInstance().trackEvent(AnalyticsTrackers.PLAYING_CATEGORY, "Shake click", "Set shaking for control music");
                MetricsManager.trackEvent("Shake click");
                setShake();
                break;
            case R.id.activity_song_detail_previous:
                MyApplication.getInstance().trackEvent(AnalyticsTrackers.PLAYING_CATEGORY, "Previous click", "Play previous song");
                MetricsManager.trackEvent("Previous click");
                previousSong();
                break;
            case R.id.activity_song_detail_play:
                MyApplication.getInstance().trackEvent(AnalyticsTrackers.PLAYING_CATEGORY, "Play/pause click", "Play/pause music");
                MetricsManager.trackEvent("Play/pause click");
                playSong();
                break;
            case R.id.activity_song_detail_next:
                MyApplication.getInstance().trackEvent(AnalyticsTrackers.PLAYING_CATEGORY, "Next click", "Play next song");
                MetricsManager.trackEvent("Next click");
                nextSong();
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
        MyApplication.getInstance().trackEvent(AnalyticsTrackers.PLAYING_CATEGORY, "Seek Music", "Set backward or forward music by progress bar");
        MetricsManager.trackEvent("Seek Music");
        seekTo(currentTime);
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

    public static int LONG_PRESS_TIME = 500;
    boolean hoding = false;
    Runnable longForwardPressed = new Runnable() {
        public void run() {
            if(hoding) {
                setForward();
                mHandler.postDelayed(this,200);
            }else{
                MyApplication.getInstance().trackEvent(AnalyticsTrackers.PLAYING_CATEGORY, "Forward long press", "Backward music by long press");
                MetricsManager.trackEvent("Forward long press");
            }
        }
    };
    Runnable longBackwardPressed = new Runnable() {
        public void run() {
            if(hoding) {
                setBackward();
                mHandler.postDelayed(this,200);
            }else{
                MyApplication.getInstance().trackEvent(AnalyticsTrackers.PLAYING_CATEGORY, "Backward long press", "Backward music by long press");
                MetricsManager.trackEvent("Backward long press");
            }
        }
    };

    private void setForward(){
        if(currentTime + 5000 > song.getSongDuration())
            currentTime = song.getSongDuration();
        else
            currentTime = currentTime + 5000;
        seekTo(currentTime);
    }

    private void setBackward(){
        if(currentTime - 5000 < 0)
            currentTime = 0;
        else
            currentTime = currentTime - 5000;
        seekTo(currentTime);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(view.getId() == R.id.activity_song_detail_forward) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    hoding = true;
                    mHandler.postDelayed(longForwardPressed, LONG_PRESS_TIME);
                    break;
                case MotionEvent.ACTION_UP:
                    setForward();
                    MyApplication.getInstance().trackEvent(AnalyticsTrackers.PLAYING_CATEGORY, "Forward click", "Forward music 5 secs");
                    MetricsManager.trackEvent("Forward click");
                    hoding = false;
                    mHandler.removeCallbacks(longForwardPressed);
                    break;
            }
        }else if(view.getId() == R.id.activity_song_detail_backward){
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    hoding = true;
                    mHandler.postDelayed(longBackwardPressed, LONG_PRESS_TIME);
                    break;
                case MotionEvent.ACTION_UP:
                    setBackward();
                    MyApplication.getInstance().trackEvent(AnalyticsTrackers.PLAYING_CATEGORY, "Backward click", "Backward music 5 secs");
                    MetricsManager.trackEvent("Backward click");
                    hoding = false;
                    mHandler.removeCallbacks(longBackwardPressed);
                    break;
            }
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if(MainActivity.active){
            super.onBackPressed();
        }else{
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            this.finish();
        }
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
