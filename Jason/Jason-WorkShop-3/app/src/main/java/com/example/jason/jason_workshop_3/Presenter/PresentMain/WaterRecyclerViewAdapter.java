package com.example.jason.jason_workshop_3.Presenter.PresentMain;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.jason.jason_workshop_3.Model.WaterDrinkingModel.CupImage;
import com.example.jason.jason_workshop_3.Model.WaterDrinkingModel.WaterViewHodler;
import com.example.jason.jason_workshop_3.R;
import com.example.jason.jason_workshop_3.View.MainView.WaterDrinkingActivity;

import java.util.List;

/**
 * Created by jason on 15/06/2016.
 */
public class WaterRecyclerViewAdapter extends RecyclerView.Adapter<WaterViewHodler> {

//    private List<CupImage> cupImages;
    private WaterDrinkingActivity mView;
    private WaterViewHodler mWaterViewHodler;
    private int lastPosition = - 1;

    public WaterRecyclerViewAdapter(WaterDrinkingActivity mView) {
        this.mView = mView;
    }

    @Override
    public WaterViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mView).inflate(R.layout.recycler_water_item, null);
        mWaterViewHodler = new WaterViewHodler(view);
        return mWaterViewHodler;
    }

    @Override
    public void onBindViewHolder(WaterViewHodler holder, int position) {
//        CupImage mCupImage = cupImages.get(position);
        holder.img_water_cup.setImageResource(R.drawable.cup);
        setAnimation(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return 10;
    }
    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(mView, R.anim.slide_in_bottom);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
}
