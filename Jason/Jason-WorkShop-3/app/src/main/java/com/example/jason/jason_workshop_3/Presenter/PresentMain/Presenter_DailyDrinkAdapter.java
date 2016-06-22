package com.example.jason.jason_workshop_3.Presenter.PresentMain;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.jason.jason_workshop_3.Model.WaterDrinkingModel.WaterDrinkingDatabase;
import com.example.jason.jason_workshop_3.Model.WaterDrinkingModel.WaterViewHodler;
import com.example.jason.jason_workshop_3.R;
import com.example.jason.jason_workshop_3.View.FeatureView.DailyDrinkActivity;

/**
 * Created by jason on 15/06/2016.
 */
public class Presenter_DailyDrinkAdapter extends RecyclerView.Adapter<WaterViewHodler> {

//    private List<CupImage> cupImages;
    private WaterDrinkingDatabase mDrinkingDatabase;
    private DailyDrinkActivity mView;
    private WaterViewHodler mWaterViewHodler;
    private int lastPosition = - 1;
    private int cup;
    public Presenter_DailyDrinkAdapter(DailyDrinkActivity mView, int cup) {
        this.mView = mView;
        this.cup = cup;
        mDrinkingDatabase = new WaterDrinkingDatabase(mView);
        mDrinkingDatabase.open();
    }

    @Override
    public WaterViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mView).inflate(R.layout.water_cup_item, null);
        mWaterViewHodler = new WaterViewHodler(view);
        return mWaterViewHodler;
    }

    @Override
    public void onBindViewHolder(WaterViewHodler holder, int position) {
//        CupImage mCupImage = cupImages.get(position);
        if (position >= cup) {
          holder.img_water_cup.setImageResource(R.drawable.checkbox_blank_circle_outline);
        } else holder.img_water_cup.setImageResource(R.drawable.check_circle);
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
