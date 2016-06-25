package com.example.jason.jason_workshop_3.Presenter.Presenter_Feature_Main.PWeeklyFitness;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.jason.jason_workshop_3.Model.ClockModel.MClockDate;
import com.example.jason.jason_workshop_3.Model.ClockModel.MCurrentDate;
import com.example.jason.jason_workshop_3.R;
import com.example.jason.jason_workshop_3.View.FeatureView.WeeklyFitnessActivity;

/**
 * Created by jason on 24/06/2016.
 */
public class PWeeklyRecyclerView extends RecyclerView.Adapter<MWeeklyViewHolder>{

    private String[] Week = {"S", "M", "T", "W", "T", "F", "S"};
    private MCurrentDate currentDate = new MCurrentDate();
    private MClockDate clockDate;
    private WeeklyFitnessActivity mView;
    private MWeeklyViewHolder viewHolder;
    private int dayofweek = 0;
    private int lastPosition = -1;

    public PWeeklyRecyclerView(WeeklyFitnessActivity mView) {
        this.mView = mView;
        clockDate = currentDate.getmClockDate();
        getDay();
    }

    @Override
    public MWeeklyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mView).inflate(R.layout.fitness_dayofweek_item, null);
        viewHolder = new MWeeklyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MWeeklyViewHolder holder, int position) {
        holder.tvDayofweek.setText(Week[position]);
        if (dayofweek == (position + 1)) {
            holder.lDayofweek.setBackgroundResource(R.drawable.circle_white_blend);
        }
        setAnimation(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return Week.length;
    }

    public void getDay(){
        String DOW = clockDate.getDay_of_Week();
        switch (DOW){
            case "Monday" : dayofweek = 2;
                break;
            case "Tuesday" : dayofweek = 3;
                break;
            case "Wednesday" : dayofweek = 4;
                break;
            case "Thursday" : dayofweek = 5;
                break;
            case "Friday" : dayofweek = 6;
                break;
            case "Saturday" : dayofweek = 7;
                break;
            case "Sunday" : dayofweek = 1;
                break;
        }
    }

    public int getDayofweek() {
        return dayofweek - 1;
    }

    public void setDayofweek(int dayofweek) {
        this.dayofweek = dayofweek;
    }

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(mView, R.anim.move_left_in_activity);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
}
