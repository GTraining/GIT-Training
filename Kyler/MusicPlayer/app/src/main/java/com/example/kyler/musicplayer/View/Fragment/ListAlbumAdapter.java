package com.example.kyler.musicplayer.View.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kyler.musicplayer.Model.Album;
import com.example.kyler.musicplayer.MyApplication;
import com.example.kyler.musicplayer.R;
import com.example.kyler.musicplayer.View.AlbumSelectedActivity;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by kyler on 27/06/2016.
 */
public class ListAlbumAdapter extends RecyclerView.Adapter<ListAlbumAdapter.AlbumViewHolder> {
    private Activity context;
    private ArrayList<Album> albums;

    public ListAlbumAdapter(Activity context, ArrayList<Album> albums) {
        this.context = context;
        this.albums = albums;
    }

    @Override
    public AlbumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_album_item,parent,false);
        return new AlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AlbumViewHolder holder, final int position) {
        Album album = albums.get(position);
        byte[] img = album.getAlbumImage();
        if(img != null){
            InputStream is = new ByteArrayInputStream(img);
            Bitmap bm = BitmapFactory.decodeStream(is);
            holder.albumImage.setImageBitmap(bm);
        }else{
            holder.albumImage.setImageResource(R.drawable.albumdefault);
        }
        holder.albumName.setText(album.getAlbumName());
        holder.albumImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyApplication.getInstance().trackEvent("Choose Album", "Choose Album from list album", "Choose album to show songs");
                Intent intent = new Intent(context, AlbumSelectedActivity.class);
                intent.putExtra(String.valueOf(R.string.albumSelected),albums.get(position).getAlbumName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    public class AlbumViewHolder extends RecyclerView.ViewHolder{
        public ImageView albumImage;
        public TextView albumName;

        public AlbumViewHolder(View view) {
            super(view);
            albumImage = (ImageView) view.findViewById(R.id.album_image);
            albumName = (TextView) view.findViewById(R.id.album_name);
        }
    }
}
