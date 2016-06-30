package com.example.jason.jason_workshop_3.Presenter.Presenter_Feature_Main;

import com.example.jason.jason_workshop_3.DialogLibrary.DialogPlus;
import com.example.jason.jason_workshop_3.DialogLibrary.GridHolder;
import com.example.jason.jason_workshop_3.DialogLibrary.Holder;
import com.example.jason.jason_workshop_3.DialogLibrary.OnDismissListener;
import com.example.jason.jason_workshop_3.DialogLibrary.ViewHolder;
import com.example.jason.jason_workshop_3.R;
import com.example.jason.jason_workshop_3.View.UserMainView.UserMainActivity;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

/**
 * Created by jason on 15/06/2016.
 */
public class PSettingActionbar implements DialogMessagaImpl {
    private DialogPlus dialogPlus;
    private UserMainActivity mView;
    private MaterialCalendarView calendarView;

    public PSettingActionbar(UserMainActivity mView) {
        this.mView = mView;
    }

    @Override
    public void show(int holderId, int gravity) {
        Holder holder;
        switch (holderId) {
            case 1:
                holder = new ViewHolder(R.layout.main_actionbar_setting);
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
        dialogPlus = DialogPlus.newDialog(mView)
                .setContentHolder(holder)
                .setGravity(gravity)
                .setOnDismissListener(dismissListener)
                .setCancelable(true)
                .create();
        calendarView = (MaterialCalendarView) dialogPlus.findViewById(R.id.calendarView);
        CalendarDay calendarDay = new CalendarDay();
        calendarView.setDateSelected(calendarDay, true);
        dialogPlus.show();
    }

    @Override
    public void dismissDialog() {
        dialogPlus.dismiss();
    }

}
