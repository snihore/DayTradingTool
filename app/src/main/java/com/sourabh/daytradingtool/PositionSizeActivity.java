package com.sourabh.daytradingtool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.sourabh.daytradingtool.Data.PositionSizeDetail;
import com.sourabh.daytradingtool.Data.TradeDetail;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Timer;

public class PositionSizeActivity extends AppCompatActivity {

    private ImageView tradeListBtn;
    private TextView quantityTv, riskToRewardTv, profitTv, profitPerShareTv, lossTv, lossPerShareTv, marginRequiredTv, actualCapitalRequiredTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position_size);

        initViews();

        try {
            PositionSizeDetail positionSizeDetail = (PositionSizeDetail) getIntent().getSerializableExtra("TRADE_DETAIL");

            setView(positionSizeDetail);

        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void setView(PositionSizeDetail positionSizeDetail) {

//        Log.i("POSITION SIZE DETAIL", positionSizeDetail.toString());

        quantityTv.setText(String.valueOf(positionSizeDetail.getQuantity()));
        riskToRewardTv.setText("Risk to Reward 1:"+positionSizeDetail.getRiskToReward());

        profitTv.setText("+"+addCommasInNumber(positionSizeDetail.getProfit())+"("+positionSizeDetail.getProfitByPercentage()+"%)");

        profitPerShareTv.setText("+"+positionSizeDetail.getProfitPerShare()+"("+positionSizeDetail.getProfitPerShareByPercentage()+"%)");
        lossTv.setText("-"+addCommasInNumber(positionSizeDetail.getLoss())+"("+positionSizeDetail.getLossByPercentage()+"%)");
        lossPerShareTv.setText("-"+positionSizeDetail.getLossPerShare()+"("+positionSizeDetail.getLossPerShareByPercentage()+"%)");
        marginRequiredTv.setText("\u20B9 "+addCommasInNumber(positionSizeDetail.getMarginRequired()));
        actualCapitalRequiredTv.setText("\u20B9 "+addCommasInNumber(positionSizeDetail.getActualCapitalRequired()));
    }

    private void initViews() {
        tradeListBtn = (ImageView) findViewById(R.id.trade_list_btn);
        quantityTv = (TextView)findViewById(R.id.quantity_tv);
        riskToRewardTv = (TextView)findViewById(R.id.risk_to_reward_tv);
        profitTv = (TextView)findViewById(R.id.profit_tv);
        profitPerShareTv = (TextView)findViewById(R.id.profit_per_share_tv);
        lossTv = (TextView)findViewById(R.id.loss_tv);
        lossPerShareTv = (TextView)findViewById(R.id.loss_per_share_tv);
        marginRequiredTv = (TextView)findViewById(R.id.margin_required_tv);
        actualCapitalRequiredTv = (TextView)findViewById(R.id.actual_capital_required_tv);


        tradeListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TradeListActivity.class);
                startActivity(intent);
            }
        });
    }

    private static String addCommasInNumber(double num){
        try{
            DecimalFormat df = new DecimalFormat("#,###.00");

            return df.format(num);
        }catch (Exception e){

        }

        return String.valueOf(num);
    }

}