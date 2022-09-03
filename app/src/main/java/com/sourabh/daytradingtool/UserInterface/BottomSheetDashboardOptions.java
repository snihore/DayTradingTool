package com.sourabh.daytradingtool.UserInterface;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.sourabh.daytradingtool.Database.FirebaseHandle;
import com.sourabh.daytradingtool.MainActivity;
import com.sourabh.daytradingtool.R;

public class BottomSheetDashboardOptions extends BottomSheetDialogFragment implements View.OnClickListener{

    private LinearLayout shareBtn, feedbackBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.bottom_sheet_dashboard_options,
                container, false);

        initViews(view);

        return view;
    }

    private void initViews(View view) {
        shareBtn = (LinearLayout) view.findViewById(R.id.share_btn);
        feedbackBtn = (LinearLayout) view.findViewById(R.id.feedback_btn);

        shareBtn.setOnClickListener(this);
        feedbackBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.share_btn:
                new FirebaseHandle((MainActivity) getActivity()).getAppURL();
                break;

            case R.id.feedback_btn:
                feedback((MainActivity) getActivity());
                break;
        }
    }

    public static void feedback(MainActivity activity){
        try{
            Uri uri = Uri.parse("market://details?id="+activity.getPackageName());
            Log.i("PACKAGE NAME", activity.getPackageName());

            Intent intent = new Intent(Intent.ACTION_VIEW, uri);

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            activity.startActivity(intent);

        }catch (ActivityNotFoundException notFoundException){

            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id="+activity.getPackageName());
            Log.i("PACKAGE NAME", activity.getPackageName());

            Intent intent = new Intent(Intent.ACTION_VIEW, uri);

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            activity.startActivity(intent);

        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
