package com.example.kyler.musicplayer.Utils;

import android.media.MediaMetadataRetriever;

import com.example.kyler.musicplayer.Model.Song;

/**
 * Created by kyler on 16/06/2016.
 */
public class Helper {
    public static Song getSong(String path){
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(path);
        String title = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        String artist = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
        String album = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
        String author = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_AUTHOR);
        String dur = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        long duration = Long.parseLong(dur);
        byte[] image = mmr.getEmbeddedPicture();
        mmr.release();
        return new Song(title,artist,album,author,duration,image,path);
    }

    public static String millisecondsToTimer(long milliseconds){
        String finalTimerString = "";
        String minutesString = "";
        String secondsString = "";

        // Convert total duration into time
        int hours = (int)( milliseconds / (1000*60*60));
        int minutes = (int)(milliseconds % (1000*60*60)) / (1000*60);
        int seconds = (int) ((milliseconds % (1000*60*60)) % (1000*60) / 1000);
        // Add hours if there
        if(hours > 0){
            finalTimerString = hours + ":";
        }

        // Prepending 0 to seconds if it is one digit
        if(seconds==0){
            secondsString = "00";
        }else if(seconds < 10){
            secondsString = "0" + seconds;
        }else{
            secondsString = "" + seconds;}

        if(minutes==0){
            minutesString = "00";
        }else if(minutes < 10){
            minutesString = "0" + minutes;
        }else{
            minutesString = "" + minutes;}

        finalTimerString = finalTimerString + minutesString + ":" + secondsString;

        // return timer string
        return finalTimerString;
    }
}
