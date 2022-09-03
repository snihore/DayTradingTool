package com.sourabh.daytradingtool.Utils;

import android.content.Context;
import android.net.ConnectivityManager;

import java.net.InetAddress;

public class FirebaseUtils {

    public static final String COLLECTION_NAME = "Day Trading Tool";
    public static final String APP_URL_DOCUMENT_NAME = "App URL";
    public static final String APP_URL_FIELD_NAME = "app_url";

    public static final String UPDATE_APP_DOCUMENT_NAME = "App Update";
    public static final String UPDATE_APP_FIELD_NAME = "version_check";


    public static boolean isNetworkAvailable(Context context) {
        try{
            ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
            return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
        }catch (Exception e){
            return false;
        }
    }

    public static boolean isInternetAvailable() {
        try {
            InetAddress address = InetAddress.getByName("www.google.com");
            return !address.equals("");
        } catch (Exception e) {
            // Log error
        }
        return false;
    }
}
