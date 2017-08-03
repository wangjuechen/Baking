package com.example.android.baking.NetworkUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class ConnectivityReceiver extends BroadcastReceiver {

    public static ConnectivityReceiverListener mConnetivityLister;

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = (networkInfo.isConnectedOrConnecting() && networkInfo != null);

        if (mConnetivityLister != null) {
            mConnetivityLister.onNetworkConnectingChanged(isConnected);
        }
    }

    public ConnectivityReceiver() {
        super();
    }

    public interface ConnectivityReceiverListener {
        void onNetworkConnectingChanged(boolean connected);
    }

    public static boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) InternetConnectivityStarter.getInstance().getApplicationContext().
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = (networkInfo.isConnectedOrConnecting() && networkInfo != null);
        return isConnected;

    }


}
