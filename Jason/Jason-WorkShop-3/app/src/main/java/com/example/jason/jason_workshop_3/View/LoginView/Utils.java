package com.example.jason.jason_workshop_3.View.LoginView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by jason on 27/06/2016.
 */
public class Utils {
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager mConnectManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (mConnectManager != null) {
            NetworkInfo[] mNetworkInfo = mConnectManager.getAllNetworkInfo();
            for (int i = 0; i < mNetworkInfo.length; i++) {
                if (mNetworkInfo[i].getState() == NetworkInfo.State.CONNECTED)
                    return true;
            }
        }
        return false;
    }
}
