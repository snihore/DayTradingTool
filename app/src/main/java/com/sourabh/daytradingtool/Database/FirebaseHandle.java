package com.sourabh.daytradingtool.Database;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sourabh.daytradingtool.BuildConfig;
import com.sourabh.daytradingtool.MainActivity;
import com.sourabh.daytradingtool.UserInterface.BottomSheetDashboardOptions;
import com.sourabh.daytradingtool.Utils.FirebaseUtils;

public class FirebaseHandle {

    private MainActivity activity;

    public FirebaseHandle(MainActivity activity) {
        this.activity = activity;
    }

    public void getAppURL(){

        try {

            if(FirebaseUtils.isNetworkAvailable(activity)){

                FirebaseFirestore db = FirebaseFirestore.getInstance();

                db.collection(FirebaseUtils.COLLECTION_NAME)
                        .document(FirebaseUtils.APP_URL_DOCUMENT_NAME)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if(documentSnapshot.exists()){
                                    String url = (String) documentSnapshot.get(FirebaseUtils.APP_URL_FIELD_NAME);
                                    Log.i("APP URL", url);

                                    share(url);
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                e.printStackTrace();
                            }
                        });

            }else {
//                Toast.makeText(activity, "Please check your network connection!", Toast.LENGTH_SHORT).show();
                share("https://play.google.com/store/apps/details?id="+activity.getPackageName());
            }

        }catch (Exception e){
            e.printStackTrace();

        }
    }

    public void updateApp(){
        try {

            if (FirebaseUtils.isNetworkAvailable(activity)) {

                FirebaseFirestore db = FirebaseFirestore.getInstance();

                db.collection(FirebaseUtils.COLLECTION_NAME)
                        .document(FirebaseUtils.UPDATE_APP_DOCUMENT_NAME)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if(documentSnapshot.exists()){

                                    checkAppVersion(documentSnapshot);

                                }
                            }
                        });
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void checkAppVersion(DocumentSnapshot documentSnapshot) {
        try{
            long versionCheck1 = (long) documentSnapshot.get(FirebaseUtils.UPDATE_APP_FIELD_NAME+"1");
            long versionCheck2 = (long) documentSnapshot.get(FirebaseUtils.UPDATE_APP_FIELD_NAME+"2");
            long versionCheck3 = (long) documentSnapshot.get(FirebaseUtils.UPDATE_APP_FIELD_NAME+"3");

            Log.i("VERSION CHECK 1", String.valueOf(versionCheck1));
            Log.i("VERSION CHECK 2", String.valueOf(versionCheck2));
            Log.i("VERSION CHECK 3", String.valueOf(versionCheck3));

            if(versionCheck1 == 0 || versionCheck2 == 0 || versionCheck3 == 0){
                return;
            }

            if(versionCheck1 != BuildConfig.VERSION_CODE && versionCheck2 != BuildConfig.VERSION_CODE && versionCheck3 != BuildConfig.VERSION_CODE){

                new AlertDialog.Builder(activity)
                        .setTitle("Update Day Trading Tool")
                        .setMessage("Day Trading Tool recommends that you update to the latest version.")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                BottomSheetDashboardOptions.feedback(activity);
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton("NO THANKS", null)
                        .setCancelable(false)
                        .show();

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void share(String url) {

        try {

            if(url == null || url.matches("")){
                return;
            }

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, "Hey! Checkout this Day Trading Tool app's latest update\n\n" +
                    url);
            intent.setType("text/plain");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            if(intent.resolveActivity(activity.getPackageManager()) != null){
                activity.startActivity(intent);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
