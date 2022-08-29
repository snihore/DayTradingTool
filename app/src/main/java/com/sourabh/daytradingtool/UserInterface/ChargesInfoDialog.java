package com.sourabh.daytradingtool.UserInterface;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.sourabh.daytradingtool.Data.CalculateCharges;
import com.sourabh.daytradingtool.PositionSizeActivity;
import com.sourabh.daytradingtool.R;
import com.sourabh.daytradingtool.TradeListActivity;

public class ChargesInfoDialog {

    private AlertDialog dialog;

    private CalculateCharges.GetIndividualCharges profitChargesObj, lossChargesObj;

    public void view(PositionSizeActivity activity, CalculateCharges.GetIndividualCharges profitChargesObj, CalculateCharges.GetIndividualCharges lossChargesObj){
        this.profitChargesObj = profitChargesObj;
        this.lossChargesObj = lossChargesObj;

        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.full_screen_alert);
        View view1 = activity.getLayoutInflater().inflate(R.layout.charges_info_layout, null);
        builder.setView(view1);
        try{
            setViews(view1);
            dialog = builder.create();
            dialog.show();
        }catch (Exception e){
            e.printStackTrace();
            if(dialog != null){
                dialog.dismiss();
            }
        }
    }

    public void view(TradeListActivity activity, CalculateCharges.GetIndividualCharges profitChargesObj, CalculateCharges.GetIndividualCharges lossChargesObj){
        this.profitChargesObj = profitChargesObj;
        this.lossChargesObj = lossChargesObj;

        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.full_screen_alert);
        View view1 = activity.getLayoutInflater().inflate(R.layout.charges_info_layout, null);
        builder.setView(view1);
        try{
            setViews(view1);
            dialog = builder.create();
            dialog.show();
        }catch (Exception e){
            e.printStackTrace();
            if(dialog != null){
                dialog.dismiss();
            }
        }
    }

    private void setViews(View view) {
        try{

            if(view == null){
                Log.i("Charges Dialog", "NULL Hai !!!");
                return;
            }

            ImageView backBtn = (ImageView)view.findViewById(R.id.charges_info_back_btn);

            backBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(dialog != null){
                        dialog.dismiss();
                    }
                }
            });


            //PROFIT
            TextView brokerageP = (TextView) view.findViewById(R.id.brokerage_profit_show);
            TextView sttP = (TextView) view.findViewById(R.id.stt_profit_show);
            TextView transactionP = (TextView) view.findViewById(R.id.transaction_charges_profit_show);
            TextView gstP = (TextView) view.findViewById(R.id.gst_profit_show);
            TextView sebiP = (TextView) view.findViewById(R.id.sebi_charges_profit_show);
            TextView stampP = (TextView) view.findViewById(R.id.stamp_charges_profit_show);
            TextView totalP = (TextView) view.findViewById(R.id.total_charges_profit_show);
            if(profitChargesObj != null){
                brokerageP.setText(String.valueOf(profitChargesObj.getBrokerage()));
                sttP.setText(String.valueOf(profitChargesObj.getStt()));
                transactionP.setText(String.valueOf(profitChargesObj.getTransaction()));
                gstP.setText(String.valueOf(profitChargesObj.getGst()));
                sebiP.setText(String.valueOf(profitChargesObj.getSebi()));
                stampP.setText(String.valueOf(profitChargesObj.getStamp()));
                totalP.setText(String.valueOf(profitChargesObj.getTotal()));
            }

            //LOSS
            TextView brokerageL = (TextView) view.findViewById(R.id.brokerage_loss_show);
            TextView sttL = (TextView) view.findViewById(R.id.stt_loss_show);
            TextView transactionL = (TextView) view.findViewById(R.id.transaction_charges_loss_show);
            TextView gstL = (TextView) view.findViewById(R.id.gst_loss_show);
            TextView sebiL = (TextView) view.findViewById(R.id.sebi_charges_loss_show);
            TextView stampL = (TextView) view.findViewById(R.id.stamp_charges_loss_show);
            TextView totalL = (TextView) view.findViewById(R.id.total_charges_loss_show);
            if(lossChargesObj != null){
                brokerageL.setText(String.valueOf(lossChargesObj.getBrokerage()));
                sttL.setText(String.valueOf(lossChargesObj.getStt()));
                transactionL.setText(String.valueOf(lossChargesObj.getTransaction()));
                gstL.setText(String.valueOf(lossChargesObj.getGst()));
                sebiL.setText(String.valueOf(lossChargesObj.getSebi()));
                stampL.setText(String.valueOf(lossChargesObj.getStamp()));
                totalL.setText(String.valueOf(lossChargesObj.getTotal()));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
