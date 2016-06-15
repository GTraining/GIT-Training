package com.example.kyler.musicplayer.Model.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.kyler.musicplayer.Model.Song;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kyler on 15/06/2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "Favorite Songs";

    private static final String TABLE_SONG_DETAIL = "SongTable";

    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_ARTIST = "artist";
    private static final String KEY_ALBUM = "album";
    private static final String KEY_AUTHOR = "author";
    private static final String KEY_DURATION = "duration";
    private static final String KEY_PATH = "path";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_SONG_DETAIL + " ( "
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_TITLE + " TEXT, "
                + KEY_ARTIST + " TEXT, "
                + KEY_ALBUM + " TEXT, "
                + KEY_AUTHOR + " TEXT, "
                + KEY_DURATION + " NUMBER, "
                + KEY_PATH + " TEXT" + " ) ";
        sqLiteDatabase.execSQL(CREATE_CONTACTS_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_SONG_DETAIL);
        onCreate(sqLiteDatabase);
    }

    public void addSong(Song song) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, song.getSongTitle());
        values.put(KEY_ARTIST, song.getSongArtist());
        values.put(KEY_ALBUM, song.getSongAlbum());
        values.put(KEY_AUTHOR, song.getSongAuthor());
        values.put(KEY_DURATION, song.getSongDuration());
        values.put(KEY_PATH, song.getSongPath());

        db.insert(TABLE_SONG_DETAIL, null, values);
        db.close();
    }

    public Song getSong(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_SONG_DETAIL +" WHERE " + KEY_ID + " == "+ id;
        Cursor cursor = db.rawQuery(selectQuery, null);
        Song song = new Song();
        if (cursor.moveToFirst()){
            song.setSongTitle(cursor.getString(0));
            song.setSongArtist(cursor.getString(1));
            song.setSongAlbum(cursor.getString(2));
            song.setSongAuthor(cursor.getString(3));
            song.setSongDuration(Long.parseLong(cursor.getString(4)));
            song.setSongPath(cursor.getString(5));
        }
        return song;
    }

    public ArrayList<Song> getRecipes(){
        ArrayList<Song> listSong = new ArrayList<Song>();
        String selectQuery = "SELECT  * FROM " + TABLE_SONG_DETAIL;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Song song = new Song();
                song.setSongTitle(cursor.getString(0));
                song.setSongArtist(cursor.getString(1));
                song.setSongAlbum(cursor.getString(2));
                song.setSongAuthor(cursor.getString(3));
                song.setSongDuration(Long.parseLong(cursor.getString(4)));
                song.setSongPath(cursor.getString(5));
                listSong.add(song);
            } while (cursor.moveToNext());
        }
        return listSong;
    }

    public int updateSong(Song song) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, song.getSongTitle());
        values.put(KEY_ARTIST, song.getSongArtist());
        values.put(KEY_ALBUM, song.getSongAlbum());
        values.put(KEY_AUTHOR, song.getSongAuthor());
        values.put(KEY_DURATION, song.getSongDuration());
        values.put(KEY_PATH, song.getSongPath());

        return db.update(TABLE_SONG_DETAIL, values, KEY_ID + " = ?",
                new String[] { String.valueOf(song.getSongPath()) });
    }
}
