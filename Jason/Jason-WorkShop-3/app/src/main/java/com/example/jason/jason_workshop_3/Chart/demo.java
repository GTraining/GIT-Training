package com.example.jason.jason_workshop_3.Chart;

import android.os.Bundle;
import android.view.WindowManager;

import com.example.jason.jason_workshop_3.Model.UserModel.Data.UserBMIDatabase;
import com.example.jason.jason_workshop_3.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.realm.implementation.RealmLineData;
import com.github.mikephil.charting.data.realm.implementation.RealmLineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import io.realm.RealmResults;

/**
 * Created by jason on 22/06/2016.
 */
public class demo extends RealmBaseActivity {

    private LineChart lineChart;
    private BarChart barChart;

    UserBMIDatabase mUserBMIDatabase = new UserBMIDatabase(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.demo);

        mUserBMIDatabase.open();

        lineChart = (LineChart) findViewById(R.id.lineChart);
        setup(lineChart);

        lineChart.setExtraBottomOffset(5f);

        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getXAxis().setDrawGridLines(false);
    }

    @Override
    protected void onResume() {
        super.onResume(); // setup realm

        mRealm.beginTransaction();

        Score score1 = new Score(100f, 0, "Peter");
        mRealm.copyToRealm(score1);
        Score score2 = new Score(110f, 1, "Lisa");
        mRealm.copyToRealm(score2);
        Score score3 = new Score(130f, 2, "Dennis");
        mRealm.copyToRealm(score3);
        Score score4 = new Score(70f, 3, "Luke");
        mRealm.copyToRealm(score4);
        Score score5 = new Score(80f, 4, "Sarah");
        mRealm.copyToRealm(score5);
        Score score6 = new Score(90f, 5, "Jason");
        mRealm.copyToRealm(score6);
        Score score7 = new Score(140f, 6, "Tony");
        mRealm.copyToRealm(score7);

        mRealm.commitTransaction();

        // add data to the chart
        setData();
    }

    private void setData() {

        // LINE-CHART
        RealmResults<Score> results = mRealm.allObjects(Score.class);

        RealmLineDataSet<Score> lineDataSet = new RealmLineDataSet<Score>(results, "totalScore", "scoreNr");
        lineDataSet.setDrawCubic(false);
        lineDataSet.setLabel("Realm LineDataSet");
        lineDataSet.setDrawCircleHole(false);
        lineDataSet.setColor(ColorTemplate.rgb("#FF5722"));
        lineDataSet.setCircleColor(ColorTemplate.rgb("#FF5722"));
        lineDataSet.setLineWidth(1.8f);
        lineDataSet.setCircleSize(3.6f);

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(lineDataSet);

        RealmLineData lineData = new RealmLineData(results, "playerName", dataSets);
        styleData(lineData);

        // set data
        lineChart.setData(lineData);
        lineChart.animateY(1400, Easing.EasingOption.EaseInOutQuart);
    }
}
