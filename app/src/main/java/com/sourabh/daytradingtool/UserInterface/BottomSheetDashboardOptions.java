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
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.sourabh.daytradingtool.AboutActivity;
import com.sourabh.daytradingtool.MainActivity;
import com.sourabh.daytradingtool.PrivacyActivity;
import com.sourabh.daytradingtool.R;

public class BottomSheetDashboardOptions extends BottomSheetDialogFragment implements View.OnClickListener{

    private LinearLayout privacyBtn, shareBtn, aboutBtn;

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
        privacyBtn = (LinearLayout) view.findViewById(R.id.privacy_btn);
        aboutBtn = (LinearLayout)view.findViewById(R.id.about_btn);

        shareBtn.setOnClickListener(this);
        privacyBtn.setOnClickListener(this);
        aboutBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.share_btn:
//                new FirebaseHandle((MainActivity) getActivity()).getAppURL();
                share("https://www.instagram.com/tradesizer/");
                break;

            case R.id.privacy_btn:
//                feedback((MainActivity) getActivity());
                Intent intent = new Intent(getActivity(), PrivacyActivity.class);
                startActivity(intent);
                dismiss();
                break;

            case R.id.about_btn:
                Intent intent2 = new Intent(getActivity(), AboutActivity.class);
                startActivity(intent2);
                dismiss();
                break;
        }
    }

    private void share(String url) {
        try {

            if(url == null || url.matches("")){
                Toast.makeText(getContext(), "Unable to share now, please try again", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, "Hey! Check out the latest update of the TradeSizer app!\n\n" +
                    url);
            intent.setType("text/plain");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            if(intent.resolveActivity(getActivity().getPackageManager()) != null){
                getActivity().startActivity(intent);
            }else{
                Toast.makeText(getContext(), "Unable to share now, please try again", Toast.LENGTH_SHORT).show();
            }

        }catch (Exception e){
            Toast.makeText(getContext(), "Unable to share now, please try again", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
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
            Toast.makeText(activity, "Please try again", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


}
