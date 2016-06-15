package com.example.jason.jason_workshop_3.Model.WaterDrinkingModel;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.jason.jason_workshop_3.R;

/**
 * Created by jason on 15/06/2016.
 */
public class WaterViewHodler extends RecyclerView.ViewHolder {
    public ImageView img_water_cup;
    public WaterViewHodler(View itemView) {
        super(itemView);
        this.img_water_cup = (ImageView) itemView.findViewById(R.id.imageView_cup);
    }
}
