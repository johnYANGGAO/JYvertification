package com.johnson.jyvertification.NetWorking.utils;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by johnsmac on 4/22/16.
 */
public class NetHasUtil {

    private static NetHasUtil ourInstance = new NetHasUtil();

    public static NetHasUtil getInstance() {
        return ourInstance;
    }

    private NetHasUtil() {
    }

    private boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

//    private boolean isWifiConnected(Context context) {
//        if (context != null) {
//            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
//                    .getSystemService(Context.CONNECTIVITY_SERVICE);
//            NetworkInfo mWiFiNetworkInfo = mConnectivityManager
//                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//            if (mWiFiNetworkInfo != null) {
//                return mWiFiNetworkInfo.isAvailable();
//            }
//        }
//        return false;
//    }
//    private boolean isMobileConnected(Context context) {
//        if (context != null) {
//            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
//                    .getSystemService(Context.CONNECTIVITY_SERVICE);
//            NetworkInfo mMobileNetworkInfo = mConnectivityManager
//                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
//            if (mMobileNetworkInfo != null) {
//                return mMobileNetworkInfo.isAvailable();
//            }
//        }
//        return false;
//    }
    public boolean hasNet(Context context,Intent intent) {

        if (!isNetworkConnected(context)) {

            context.sendBroadcast(intent);
            return false;
        }

        return true;
    }

}
