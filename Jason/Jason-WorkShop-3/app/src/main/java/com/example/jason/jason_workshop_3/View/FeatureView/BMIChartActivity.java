package com.example.jason.jason_workshop_3.View.FeatureView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.jason.jason_workshop_3.ChartLibrary.RealmBaseActivity;
import com.example.jason.jason_workshop_3.ChartLibrary.Score;
import com.example.jason.jason_workshop_3.Presenter.PresentMain.Presenter_BMI_Chart_Line;
import com.example.jason.jason_workshop_3.R;
import com.example.jason.jason_workshop_3.View.UserMainView.UserMainActivity;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.realm.implementation.RealmLineData;
import com.github.mikephil.charting.data.realm.implementation.RealmLineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmResults;

public class BMIChartActivity extends RealmBaseActivity {
    private LineChart lineChart;
    private Presenter_BMI_Chart_Line presenterBmiChartLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_bmi_chart);
        presenterBmiChartLine = new Presenter_BMI_Chart_Line(this);

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
        List<Score> scoreList = presenterBmiChartLine.setScore();
        for (int i = 0; i < scoreList.size(); i ++){
            mRealm.copyToRealm(scoreList.get(i));
        }
        mRealm.commitTransaction();
        setData();
    }

    private void setData() {

        // LINE-CHART
        RealmResults<Score> results = mRealm.allObjects(Score.class);

        RealmLineDataSet<Score> lineDataSet = new RealmLineDataSet<Score>(results, "totalScore", "scoreNr");
        lineDataSet.setDrawCubic(false);
        lineDataSet.setLabel("YOUR BMI");
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

    public void onclickBack(View v){
        backEvent();
    }

    @Override
    public void onBackPressed() {
        backEvent();
    }

    public void startActivitys(Class mClass, String intent){
        Intent mIntent = new Intent(this, mClass);
        mIntent.putExtra("Intent", intent);
        startActivity(mIntent);
    }

    public void backEvent(){
        String intent = getIntent().getStringExtra("Intent");
        if (intent.equals("1")){
            startActivitys(MonthlyCheckBMIActivity.class, "3");
        }else startActivitys(UserMainActivity.class, "None");
    }
}
