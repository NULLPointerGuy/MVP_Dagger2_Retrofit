package com.karthik.todo;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.ContextCompat;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by karthikrk on 12/08/17.
 */

public class Utils {
    public static boolean isInternetAvailable(Context context){
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    public static boolean isLocationPermissionGranted(Context context){
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            return false;
        }
        return true;
    }

    public static String getComposedTime(Context context,Calendar calendar){
        String month = calendar
                .getDisplayName(Calendar.MONTH,Calendar.SHORT,Locale.getDefault());
        return String.format(context.getString(R.string.remindersettime),month,
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.HOUR),calendar.get(Calendar.MINUTE));
    }
}
