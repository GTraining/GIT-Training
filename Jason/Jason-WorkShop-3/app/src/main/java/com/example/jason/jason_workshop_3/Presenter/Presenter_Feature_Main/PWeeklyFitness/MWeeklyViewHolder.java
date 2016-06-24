package com.example.jason.jason_workshop_3.Presenter.Presenter_Feature_Main.PWeeklyFitness;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jason.jason_workshop_3.R;

/**
 * Created by jason on 24/06/2016.
 */
public class MWeeklyViewHolder extends RecyclerView.ViewHolder{
    public TextView tvDayofweek;
    public RelativeLayout lDayofweek;

    public MWeeklyViewHolder(View itemView) {
        super(itemView);
        this.tvDayofweek = (TextView) itemView.findViewById(R.id.textView_dayofweek);
        this.lDayofweek = (RelativeLayout) itemView.findViewById(R.id.layout_dayofweek);
    }
}
