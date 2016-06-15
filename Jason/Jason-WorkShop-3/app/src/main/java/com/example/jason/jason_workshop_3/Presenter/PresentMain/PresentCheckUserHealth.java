package com.example.jason.jason_workshop_3.Presenter.PresentMain;

import android.content.Intent;
import android.widget.TextView;

import com.example.jason.jason_workshop_3.DialogLibrary.DialogPlus;
import com.example.jason.jason_workshop_3.DialogLibrary.GridHolder;
import com.example.jason.jason_workshop_3.DialogLibrary.Holder;
import com.example.jason.jason_workshop_3.DialogLibrary.OnDismissListener;
import com.example.jason.jason_workshop_3.DialogLibrary.ViewHolder;
import com.example.jason.jason_workshop_3.Model.UserModel.Entity.UserBMI;
import com.example.jason.jason_workshop_3.Model.UserModel.Entity.UserCheckInfo;
import com.example.jason.jason_workshop_3.Presenter.PresentLogin.UserMangement;
import com.example.jason.jason_workshop_3.R;
import com.example.jason.jason_workshop_3.View.CustomClockView.CustomClock;
import com.example.jason.jason_workshop_3.View.MainView.NewUser_CreateData;

/**
 * Created by jason on 14/06/2016.
 */
public class PresentCheckUserHealth implements DialogMessagaImpl{
    private TextView txv_checkBMI, txv_BMI;
    private DialogPlus dialog;
    private UserBMI mUserBMI;
    private NewUser_CreateData mView;
    private UserMangement mUsermangement;
    private String Username;
    private float BMI = 0;

    public PresentCheckUserHealth(NewUser_CreateData mView, String username) {
        this.mView = mView;
        this.Username = username;
        mUsermangement = new UserMangement(mView);
    }

    @Override
    public void show(int holderId, int gravity) {
        Holder holder;
        switch (holderId) {
            case 1:
                holder = new ViewHolder(R.layout.bmi_dialog);
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

        mUserBMI = mView.getUserHealth();
        BMI = mUserBMI.getBMI();
        txv_checkBMI = (TextView) dialog.findViewById(R.id.textView_checkBMI);
        txv_BMI = (TextView) dialog.findViewById(R.id.textView_BMI);
        txv_checkBMI.setText(mUserBMI.convertBMI(BMI));
        txv_BMI.setText("YOUR BMI: " + BMI);

        dialog.show();
    }
    public void startImproveHealth(){
        UserCheckInfo mCheckLogin = mUsermangement.checkExisting(Username);
        mUsermangement.UpdateStatus(mCheckLogin.getID(), "Older");
        Intent mIntent = new Intent(mView, CustomClock.class);
        mIntent.putExtra("BMI", "" + BMI);
        mView.startActivity(mIntent);
    }
    public void dismissDialog(){
        dialog.dismiss();
    }
}
