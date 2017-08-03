package com.example.android.baking.NetworkUtils;


import android.app.Application;

public class InternetConnectivityStarter extends Application {
    private static InternetConnectivityStarter mStarter;

    @Override
    public void onCreate() {
        super.onCreate();

        mStarter = this;
    }

    public static synchronized InternetConnectivityStarter getInstance() {
        return mStarter;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.mConnetivityLister = listener;
    }
}
