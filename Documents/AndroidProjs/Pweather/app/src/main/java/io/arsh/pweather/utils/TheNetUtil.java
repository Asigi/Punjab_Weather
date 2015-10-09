package io.arsh.pweather.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by Arsh on October 3, 2015
 */
public class TheNetUtil {


    /**
     * Check to see if the network is available.
     * @return true if there is access to the internet, false otherwise.
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (info != null && info.isConnected()) {
            isAvailable = true;
        }
        Log.v("TheNetUtil", "isNetworkAvailable(). net is " + isAvailable);
        return isAvailable;
    }
}
