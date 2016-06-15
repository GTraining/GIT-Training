package com.example.jason.jason_workshop_3.Presenter.PresentMain;

import com.example.jason.jason_workshop_3.DialogLibrary.Holder;
import com.example.jason.jason_workshop_3.DialogLibrary.OnDismissListener;

/**
 * Created by jason on 14/06/2016.
 */
public interface DialogMessagaImpl {
    void show(int holderId, int gravity);
    void DialogHandle(Holder holder, int gravity, OnDismissListener dismissListener);
    void dismissDialog();
}
