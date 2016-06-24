package com.example.jason.jason_workshop_3.Model.FeatureModel;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jason.jason_workshop_3.R;

/**
 * Created by jason on 24/06/2016.
 */
public class FoodViewHolder extends RecyclerView.ViewHolder {

    public TextView tvFoodName;
    public ImageView imgFood;

    public FoodViewHolder(View itemView) {
        super(itemView);
        this.tvFoodName = (TextView) itemView.findViewById(R.id.textView_foodName);
        this.imgFood = (ImageView) itemView.findViewById(R.id.image_food);
    }
}