package com.example.jason.jason_workshop_3.View.FeatureView;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jason.jason_workshop_3.Presenter.Presenter_Feature_Main.PDailyDrink.PDaiLyDrinkUpdate;
import com.example.jason.jason_workshop_3.Presenter.Presenter_Feature_Main.PDailyDrink.PDailyDrinkAdapter;
import com.example.jason.jason_workshop_3.R;
import com.example.jason.jason_workshop_3.View.UserMainView.UserMainActivity;

public class DailyDrinkActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private PDailyDrinkAdapter mAdapter;
    private int cup = 0;
    private TextView txv_cupamount;
    private ImageView img_water;
    private PDaiLyDrinkUpdate pDaiLyDrinkUpdate;
    private RelativeLayout layout_add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.water_management);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        txv_cupamount = (TextView) findViewById(R.id.textView_amount);
        img_water = (ImageView) findViewById(R.id.imageView_water);
        layout_add = (RelativeLayout) findViewById(R.id.layout_add);

        pDaiLyDrinkUpdate = new PDaiLyDrinkUpdate(this);
        cup = pDaiLyDrinkUpdate.checkExisted();
        if (cup == 10){
            layout_add.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(), "You've drink enough amount of water at today!", Toast.LENGTH_LONG).show();
        }

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 10));
        mAdapter = new PDailyDrinkAdapter(this, cup);
        txv_cupamount.setText("" + (cup * 30 / 3));
        img_water.getLayoutParams().height = coverheighttodp(cup * 30);
        img_water.requestLayout();
        mRecyclerView.setAdapter(mAdapter);

    }
    public void onclickAddCup(View v){
        if (cup < 10) {
            cup ++;
            txv_cupamount.setText("" + cup * 10);
            img_water.getLayoutParams().height = coverheighttodp(cup * 30);
            img_water.requestLayout();
            pDaiLyDrinkUpdate.updateWaterCup();

            mAdapter = new PDailyDrinkAdapter(DailyDrinkActivity.this, cup);
            mRecyclerView.setAdapter(mAdapter);
        }
    }
    public int coverheighttodp(int height){
        int heightdp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, height, getResources().getDisplayMetrics());
        return heightdp;
    }

    public void onclickback(View v){
        Intent mIntent = new Intent(this, UserMainActivity.class);
        startActivity(mIntent);
    }
}
