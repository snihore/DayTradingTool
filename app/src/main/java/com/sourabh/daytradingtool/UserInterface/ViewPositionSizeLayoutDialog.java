package com.sourabh.daytradingtool.UserInterface;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import com.sourabh.daytradingtool.MainActivity;
import com.sourabh.daytradingtool.R;
import com.sourabh.daytradingtool.TradeListActivity;

public class ViewPositionSizeLayoutDialog {

    private TradeListActivity activity;

    public ViewPositionSizeLayoutDialog(TradeListActivity activity) {
        this.activity = activity;
    }

    public void view(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.full_screen_alert);
        View view1 = activity.getLayoutInflater().inflate(R.layout.view_position_size_layout_dialog, null);
        builder.setView(view1);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
