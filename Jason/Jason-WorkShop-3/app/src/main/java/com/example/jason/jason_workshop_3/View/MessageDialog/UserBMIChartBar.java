package com.example.jason.jason_workshop_3.View.MessageDialog;

import com.example.jason.jason_workshop_3.DialogLibrary.DialogPlus;
import com.example.jason.jason_workshop_3.DialogLibrary.GridHolder;
import com.example.jason.jason_workshop_3.DialogLibrary.Holder;
import com.example.jason.jason_workshop_3.DialogLibrary.OnDismissListener;
import com.example.jason.jason_workshop_3.DialogLibrary.ViewHolder;
import com.example.jason.jason_workshop_3.Model.UserModel.Data.UserBMIDatabase;
import com.example.jason.jason_workshop_3.Model.UserModel.Entity.UserSetUp;
import com.example.jason.jason_workshop_3.Model.UserModel.Entity.UserBMI;
import com.example.jason.jason_workshop_3.Model.UserModel.Entity.CurrentLogin;
import com.example.jason.jason_workshop_3.Presenter.Presenter_LogIn_SignUp.Presenter_UserManagement;
import com.example.jason.jason_workshop_3.Presenter.Presenter_Feature_Main.DialogMessagaImpl;
import com.example.jason.jason_workshop_3.R;
import com.example.jason.jason_workshop_3.View.UserMainView.UserMainActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jason on 16/06/2016.
 */
public class UserBMIChartBar implements DialogMessagaImpl {
    DialogPlus dialog;
    private BarChart barChart;
    private ArrayList<BarEntry> barEntries;
    private UserMainActivity mView;
    private UserBMIDatabase mUserBMIDatabase;
    private List<UserBMI> mUserBMIs = new ArrayList<>();
    private Presenter_UserManagement mUserManagement;
    private UserSetUp mSetUp;

    public UserBMIChartBar(UserMainActivity view) {
        this.mView = view;
        mUserBMIDatabase = new UserBMIDatabase(mView);
        mUserManagement = new Presenter_UserManagement(mView);
        mUserBMIDatabase.open();
        CurrentLogin mCurrentLogin = mUserManagement.checkCurrentLogin();
        mUserBMIs = mUserBMIDatabase.GETLIST(mCurrentLogin.USERNAME);
    }

    @Override
    public void show(int holderId, int gravity) {
        Holder holder;
        switch (holderId) {
            case 1:
                holder = new ViewHolder(R.layout.bmi_history_chartbar_dialog);
                break;
            default:
                holder = new GridHolder(1);
        }
        OnDismissListener dismissListener = new OnDismissListener() {
            @Override
            public void onDismiss(DialogPlus dialog) {
            }
        };
        DialogHandle(holder, gravity, dismissListener);
    }

    @Override
    public void DialogHandle(Holder holder, int gravity, OnDismissListener dismissListener) {
        dialog = DialogPlus.newDialog(mView)
                .setContentHolder(holder)
                .setGravity(gravity)
                .setOnDismissListener(dismissListener)
                .setCancelable(true)
                .create();
        barChart = (BarChart) dialog.findViewById(R.id.bargraph);
        CreateBarGraph(mUserBMIs);
        mUserBMIDatabase.close();
        mUserManagement.closeDatabase();
        dialog.show();
    }

    @Override
    public void dismissDialog() {
        dialog.dismiss();
    }

    public void CreateBarGraph(List<UserBMI> userBMIs){
        barEntries = new ArrayList<>();
        List<String> DateCheck = new ArrayList<>();
        for (int j = 0; j < userBMIs.size(); j++) {
            float BMI = userBMIs.get(j).getBMI()/10;
            String date = userBMIs.get(j).getCHECKTIME();
            DateCheck.add(date);
            barEntries.add(new BarEntry(BMI, j));
        }
        BarDataSet barDataSet = new BarDataSet(barEntries, "BMI");
        BarData barData = new BarData(DateCheck, barDataSet);
        barChart.setData(barData);
        barChart.setDescription("BMI Bar Graph");
    }
}
