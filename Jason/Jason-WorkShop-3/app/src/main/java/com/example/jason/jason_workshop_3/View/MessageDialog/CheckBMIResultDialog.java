package com.example.jason.jason_workshop_3.View.MessageDialog;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jason.jason_workshop_3.DialogLibrary.DialogPlus;
import com.example.jason.jason_workshop_3.DialogLibrary.GridHolder;
import com.example.jason.jason_workshop_3.DialogLibrary.Holder;
import com.example.jason.jason_workshop_3.DialogLibrary.OnDismissListener;
import com.example.jason.jason_workshop_3.DialogLibrary.ViewHolder;
import com.example.jason.jason_workshop_3.Model.ClockModel.MClockDate;
import com.example.jason.jason_workshop_3.Model.ClockModel.MCurrentDate;
import com.example.jason.jason_workshop_3.Model.UserModel.Data.UserBMIDatabase;
import com.example.jason.jason_workshop_3.Model.UserModel.Data.UserDatabase;
import com.example.jason.jason_workshop_3.Model.UserModel.Entity.UserBMI;
import com.example.jason.jason_workshop_3.Model.UserModel.Entity.CurrentLogin;
import com.example.jason.jason_workshop_3.Presenter.Presenter_LogIn_SignUp.Presenter_UserManagement;
import com.example.jason.jason_workshop_3.Presenter.Presenter_Feature_Main.DialogMessagaImpl;
import com.example.jason.jason_workshop_3.R;
import com.example.jason.jason_workshop_3.View.FeatureView.MonthlyCheckBMIActivity;
import com.example.jason.jason_workshop_3.View.UserMainView.UserMainActivity;

import java.util.List;

/**
 * Created by jason on 14/06/2016.
 */
public class CheckBMIResultDialog implements DialogMessagaImpl {
    private TextView txv_checkBMI, txv_BMI;
    private DialogPlus dialog;
    private List<String> mList;
    private UserBMI mUserBMI;
    private MonthlyCheckBMIActivity mView;
    private UserDatabase userDatabase;
    private CurrentLogin mCurrentLogin;
    private UserBMIDatabase mUserBMIDatabase;
    private MCurrentDate mCurrentDate = new MCurrentDate();
    private String currentdate = "00-00-0000";
    private float mBMI = 0;

    public CheckBMIResultDialog(MonthlyCheckBMIActivity mView) {
        this.mView = mView;
        mUserBMIDatabase = new UserBMIDatabase(mView);
        mUserBMIDatabase.open();
        userDatabase = new UserDatabase(mView);
        userDatabase.open();
        mCurrentLogin =  userDatabase.CheckCurrentLogin();
        MClockDate mClockDate = mCurrentDate.getmClockDate();
        currentdate = mClockDate.getDay() + "/" + mClockDate.getMonthNumber() + "/" + mClockDate.getYear();
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

        txv_checkBMI = (TextView) dialog.findViewById(R.id.textView_checkBMI);
        txv_BMI = (TextView) dialog.findViewById(R.id.textView_BMI);
        Button btnImprove = (Button) dialog.findViewById(R.id.button_improve);
        RelativeLayout layout_improve  = (RelativeLayout) dialog.findViewById(R.id.layout_improve);

        mList = mView.getUserHealth();
        mUserBMI = new UserBMI(mCurrentLogin.getUSERNAME(), mList.get(0), mList.get(1), mList.get(2), currentdate);
        mBMI = mUserBMI.getBMI();

        //If user login, which is the first of user, button start improve healthy will be Visible.
        if (mView.checkIntentID() != 1){
            layout_improve.setVisibility(View.INVISIBLE);
        }

        //Create alarm ti remind User check their BMI every Month, and save their index into database
        else if (mView.checkIntentID() == 3) {
            mView.setCheckBMIAlarm();
            mUserBMIDatabase.INSERT(mUserBMI);
        }

        txv_checkBMI.setText(mUserBMI.convertBMI(mBMI));
        txv_BMI.setText("YOUR BMI: " + mBMI);

        btnImprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startImproveHealth(mUserBMI, mBMI);
            }
        });

        dialog.show();
    }

    public void startImproveHealth(UserBMI userBMI, float fBMI){
        mView.setCheckBMIAlarm();
        mUserBMIDatabase.INSERT(userBMI);
        userDatabase.UpdateHealthStatus(mCurrentLogin.getID(), "Older");
        userDatabase.close();
        Intent mIntent = new Intent(mView, UserMainActivity.class);
        mView.startActivity(mIntent);
    }

    public void dismissDialog(){
        dialog.dismiss();
    }


}
