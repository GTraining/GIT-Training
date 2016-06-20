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
        String title = "";
        String artist = "";
        String album = "";
        String author = "";
        if(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE) != null){
            title = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        }else{
            title = "no name";
        }
        if(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST) != null){
            artist = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
        }else{
            artist = "not available";
        }
        if( mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM) != null){
            album = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
        }else{
            album = "not available";
        }
        if(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_AUTHOR) != null){
            author = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_AUTHOR);
        }else{
            author = "not available";
        }
        String dur = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        long duration = Long.parseLong(dur);
        byte[] image = mmr.getEmbeddedPicture();
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
