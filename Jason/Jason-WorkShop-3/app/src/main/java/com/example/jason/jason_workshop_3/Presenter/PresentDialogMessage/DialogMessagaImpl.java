package com.example.jason.jason_workshop_3.Presenter.PresentDialogMessage;

import com.example.jason.jason_workshop_3.Dialog.Holder;
import com.example.jason.jason_workshop_3.Dialog.OnDismissListener;

/**
 * Created by jason on 14/06/2016.
 */
public interface DialogMessagaImpl {
    void show(int holderId, int gravity);
    void DialogHandle(Holder holder, int gravity, OnDismissListener dismissListener);
}
