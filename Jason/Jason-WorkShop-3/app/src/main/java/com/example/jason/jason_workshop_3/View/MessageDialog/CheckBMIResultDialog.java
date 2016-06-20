package com.example.jason.jason_workshop_3.View.MessageDialog;

import android.content.Intent;
import android.widget.TextView;

import com.example.jason.jason_workshop_3.DialogLibrary.DialogPlus;
import com.example.jason.jason_workshop_3.DialogLibrary.GridHolder;
import com.example.jason.jason_workshop_3.DialogLibrary.Holder;
import com.example.jason.jason_workshop_3.DialogLibrary.OnDismissListener;
import com.example.jason.jason_workshop_3.DialogLibrary.ViewHolder;
import com.example.jason.jason_workshop_3.Model.ClockModel.ClockDate;
import com.example.jason.jason_workshop_3.Model.ClockModel.CurrentDate;
import com.example.jason.jason_workshop_3.Model.UserModel.Data.UserBMIDatabase;
import com.example.jason.jason_workshop_3.Model.UserModel.Entity.UserBMI;
import com.example.jason.jason_workshop_3.Model.UserModel.Entity.UserCheckCurrentLogin;
import com.example.jason.jason_workshop_3.Presenter.PresentLogin.UserManagement;
import com.example.jason.jason_workshop_3.Presenter.PresentMain.DialogMessagaImpl;
import com.example.jason.jason_workshop_3.R;
import com.example.jason.jason_workshop_3.View.UserMainView.UserMainActivity;
import com.example.jason.jason_workshop_3.View.FeatureView.UserCheckBMIActivity;

import java.util.List;

/**
 * Created by jason on 14/06/2016.
 */
public class CheckBMIResultDialog implements DialogMessagaImpl {
    private TextView txv_checkBMI, txv_BMI;
    private DialogPlus dialog;
    private List<String> mList;
    private UserBMI mUserBMI;
    private UserCheckBMIActivity mView;
    private UserManagement mUsermanagement;
    private UserCheckCurrentLogin mCurrentLogin;
    private UserBMIDatabase mUserBMIDatabase;
    private CurrentDate mCurrentDate = new CurrentDate();
    private  String date = "00-00-0000";
    private float BMI = 0;

    public CheckBMIResultDialog(UserCheckBMIActivity mView) {
        this.mView = mView;
        mUserBMIDatabase = new UserBMIDatabase(mView);
        mUserBMIDatabase.open();
        mUsermanagement = new UserManagement(mView);
        mCurrentLogin =  mUsermanagement.checkCurrentLogin();
        ClockDate mClockDate = mCurrentDate.getmClockDate();
        date = mClockDate.getDay() + "-" + mClockDate.getMonth() + "-" + mClockDate.getYear();
    }

    @Override
    public void show(int holderId, int gravity) {
        Holder holder;
        switch (holderId) {
            case 1:
                holder = new ViewHolder(R.layout.bmi_result_dialog);
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
        mList = mView.getUserHealth();
        mUserBMI = new UserBMI(mCurrentLogin.getUSERNAME(), mList.get(0),mList.get(1), date );
        BMI = mUserBMI.getBMI();
        txv_checkBMI = (TextView) dialog.findViewById(R.id.textView_checkBMI);
        txv_BMI = (TextView) dialog.findViewById(R.id.textView_BMI);
        txv_checkBMI.setText(mUserBMI.convertBMI(BMI));
        txv_BMI.setText("YOUR BMI: " + BMI);
        mUserBMIDatabase.INSERT(mUserBMI);
        dialog.show();
    }
    public void startImproveHealth(){
        mUsermanagement.UpdateBMI(mCurrentLogin.getID(), mUserBMI.convertBMI(BMI));
        Intent mIntent = new Intent(mView, UserMainActivity.class);
        mView.startActivity(mIntent);
    }
    public void dismissDialog(){
        dialog.dismiss();
    }
}
