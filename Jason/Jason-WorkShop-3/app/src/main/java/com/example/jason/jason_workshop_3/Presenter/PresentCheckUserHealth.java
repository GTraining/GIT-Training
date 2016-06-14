package com.example.jason.jason_workshop_3.Presenter;

import android.content.Intent;
import android.widget.TextView;

import com.example.jason.jason_workshop_3.Dialog.DialogPlus;
import com.example.jason.jason_workshop_3.Dialog.GridHolder;
import com.example.jason.jason_workshop_3.Dialog.Holder;
import com.example.jason.jason_workshop_3.Dialog.OnDismissListener;
import com.example.jason.jason_workshop_3.Dialog.ViewHolder;
import com.example.jason.jason_workshop_3.Model.UserData.UserHealth;
import com.example.jason.jason_workshop_3.Presenter.PresentDialogMessage.DialogMessagaImpl;
import com.example.jason.jason_workshop_3.R;
import com.example.jason.jason_workshop_3.View.CustomClock;
import com.example.jason.jason_workshop_3.View.NewUser_CreateData;

/**
 * Created by jason on 14/06/2016.
 */
public class PresentCheckUserHealth implements DialogMessagaImpl{
    private TextView txv_checkBMI, txv_BMI;
    private DialogPlus dialog;
    private UserHealth mUserHealth;
    private NewUser_CreateData mView;
    private double BMI;

    public PresentCheckUserHealth(NewUser_CreateData mView) {
        this.mView = mView;
        mUserHealth = mView.getUserHealth();
        BMI = mUserHealth.convertBMI();
    }

    @Override
    public void show(int holderId, int gravity) {
        Holder holder;
        switch (holderId) {
            case 1:
                holder = new ViewHolder(R.layout.user_register_dialog);
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
        txv_checkBMI.setText(mUserHealth.getBMI(BMI));
        txv_BMI.setText("YOUR BMI" + BMI);
    }
    public void startImproveHealth(){
        Intent mIntent = new Intent(mView, CustomClock.class);
        mIntent.putExtra("BMI", ""+BMI);
        mView.startActivity(mIntent);
    }
}
