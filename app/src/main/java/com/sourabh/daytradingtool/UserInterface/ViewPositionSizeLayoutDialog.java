package com.sourabh.daytradingtool.UserInterface;

import android.content.ClipboardManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import com.sourabh.daytradingtool.Data.CalculateCharges;
import com.sourabh.daytradingtool.Data.PositionSizeDetail;
import com.sourabh.daytradingtool.Data.TradeDetail;
import com.sourabh.daytradingtool.Data.TradeDetailPOJO;
import com.sourabh.daytradingtool.Data.TradingCapitalData;
import com.sourabh.daytradingtool.R;
import com.sourabh.daytradingtool.TradeListActivity;
import com.sourabh.daytradingtool.Utils.FormatUtils;

public class ViewPositionSizeLayoutDialog {

    private TradeListActivity activity;
    private TradeDetailPOJO tradeDetailPOJO;

    private TextView quantityTv, riskToRewardTv, profitTv, profitPerShareTv, lossTv, lossPerShareTv, marginRequiredTv, actualCapitalRequiredTv;
    private TextView stockTitleTv, entryPriceTv, stoplossTv, exitPriceTv, profitChargesTv, lossChargesTv;

    private ImageView backBtn, copyBtn, chargesInfoBtn, marginInfoBtn, entryPriceCopyBtn, stoplossCopyBtn, exitPriceCopyBtn;

    private RelativeLayout inputsLayout;

    private PositionSizeDetail positionSizeDetail;
    private TradingCapitalData tradingCapitalData;
    private String stockTitle;

    private CalculateCharges.GetIndividualCharges profitChargesObj, lossChargesObj;

    private AlertDialog dialog;


    public ViewPositionSizeLayoutDialog(TradeListActivity activity, TradeDetailPOJO tradeDetailPOJO, TradingCapitalData tradingCapitalData, String stockTitle) {
        this.activity = activity;
        this.tradeDetailPOJO = tradeDetailPOJO;
        this.stockTitle = stockTitle;
        this.tradingCapitalData = tradingCapitalData;

    }

    public void view(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.full_screen_alert);
        View view1 = activity.getLayoutInflater().inflate(R.layout.view_position_size_layout_dialog, null);
        builder.setView(view1);
        try{
            initViews(view1);
            setViews();
            dialog = builder.create();
            dialog.show();
        }catch (Exception e){
            e.printStackTrace();
            if(dialog != null){
                dialog.dismiss();
            }
        }
    }

    private void initViews(View view) {
        quantityTv = (TextView)view.findViewById(R.id.quantity_tv);
        riskToRewardTv = (TextView)view.findViewById(R.id.risk_to_reward_tv);
        profitTv = (TextView)view.findViewById(R.id.profit_tv);
        profitPerShareTv = (TextView)view.findViewById(R.id.profit_per_share_tv);
        lossTv = (TextView)view.findViewById(R.id.loss_tv);
        lossPerShareTv = (TextView)view.findViewById(R.id.loss_per_share_tv);
        marginRequiredTv = (TextView)view.findViewById(R.id.margin_required_tv);
        actualCapitalRequiredTv = (TextView)view.findViewById(R.id.actual_capital_required_tv);
        stockTitleTv = (TextView) view.findViewById(R.id.stock_title_tv);
        entryPriceTv = (TextView) view.findViewById(R.id.entry_price_tv);
        stoplossTv = (TextView) view.findViewById(R.id.stoploss_tv);
        exitPriceTv = (TextView) view.findViewById(R.id.exit_price_tv);
        backBtn = (ImageView) view.findViewById(R.id.view_position_size_back_btn);
        profitChargesTv = (TextView)view.findViewById(R.id.profit_charges);
        lossChargesTv = (TextView)view.findViewById(R.id.loss_charges);
        inputsLayout = (RelativeLayout)view.findViewById(R.id.view_position_size_layout_inputs_layout);
        copyBtn = (ImageView)view.findViewById(R.id.copy_img_btn);
        chargesInfoBtn = (ImageView)view.findViewById(R.id.charges_info_btn);
        marginInfoBtn = (ImageView)view.findViewById(R.id.margin_info_btn);
        entryPriceCopyBtn = (ImageView)view.findViewById(R.id.entry_price_copy);
        stoplossCopyBtn = (ImageView)view.findViewById(R.id.stoploss_copy);
        exitPriceCopyBtn = (ImageView)view.findViewById(R.id.exit_price_copy);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        chargesInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    ChargesInfoDialog chargesInfoDialog = new ChargesInfoDialog();

                    chargesInfoDialog.view(activity, profitChargesObj, lossChargesObj);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        marginInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    MarginInfoDialog marginInfoDialog = new MarginInfoDialog();

                    marginInfoDialog.view(activity, tradingCapitalData);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        //COPY
        copyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                copyValue(quantityTv, "Quantity");
            }
        });
        //COPY
        entryPriceCopyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                copyValue(entryPriceTv, "Entry Price");
            }
        });
        //COPY
        stoplossCopyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                copyValue(stoplossTv, "Stoploss");
            }
        });
        //COPY
        exitPriceCopyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                copyValue(exitPriceTv, "Exit Price");
            }
        });

    }

    private void copyValue(TextView textView, String tag) {
        try{

            if(textView != null && !textView.getText().toString().trim().matches("")){

                String copyText = textView.getText().toString().trim();

                int sdk = android.os.Build.VERSION.SDK_INT;
                if (sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
                    ClipboardManager clipboard = (ClipboardManager)
                            activity.getSystemService(activity.getApplicationContext().CLIPBOARD_SERVICE);
                    clipboard.setText(copyText);
                } else {
                    ClipboardManager clipboard = (ClipboardManager)
                            activity.getSystemService(activity.getApplicationContext().CLIPBOARD_SERVICE);
                    android.content.ClipData clip = android.content.ClipData
                            .newPlainText(tag, copyText);
                    clipboard.setPrimaryClip(clip);
                }

                Toast.makeText(activity, copyText+" "+tag+" copied", Toast.LENGTH_SHORT).show();

            }else{
                Toast.makeText(activity, "Can't copied", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(activity, "Can't copied", Toast.LENGTH_SHORT).show();
        }
    }

    private void setViews() {

        try{
            if(tradeDetailPOJO == null || stockTitle == null){
                return;
            }

            stockTitleTv.setText(stockTitle);
            entryPriceTv.setText(String.valueOf(tradeDetailPOJO.getEntryPrice()));
            stoplossTv.setText(String.valueOf(tradeDetailPOJO.getStoploss()));
            exitPriceTv.setText(String.valueOf(tradeDetailPOJO.getExitPrice()));


            TradeDetail tradeDetail = new TradeDetail(
                    tradeDetailPOJO.getEntryPrice(),
                    tradeDetailPOJO.isBuy(),
                    tradeDetailPOJO.getStoploss(),
                    "PRICE",
                    tradeDetailPOJO.getExitPrice(),
                    "PRICE",
                    tradingCapitalData
            );

            if(tradeDetail != null && tradeDetail.getPositionSizeDetail() != null){

                positionSizeDetail = tradeDetail.getPositionSizeDetail();

                if(positionSizeDetail == null || tradingCapitalData == null){
                    return;
                }

                //Set Views
                quantityTv.setText(String.valueOf(positionSizeDetail.getQuantity()));
                riskToRewardTv.setText("Risk to Reward 1:"+positionSizeDetail.getRiskToReward());

                profitTv.setText("+"+ FormatUtils.addCommasInNumber(positionSizeDetail.getProfit())+"("+positionSizeDetail.getProfitByPercentage()+"%)");

                profitPerShareTv.setText("+"+positionSizeDetail.getProfitPerShare()+"("+positionSizeDetail.getProfitPerShareByPercentage()+"%)");
                lossTv.setText("-"+FormatUtils.addCommasInNumber(positionSizeDetail.getLoss())+"("+positionSizeDetail.getLossByPercentage()+"%)");
                lossPerShareTv.setText("-"+positionSizeDetail.getLossPerShare()+"("+positionSizeDetail.getLossPerShareByPercentage()+"%)");
                marginRequiredTv.setText("\u20B9 "+FormatUtils.addCommasInNumber(positionSizeDetail.getMarginRequired())+"("+tradingCapitalData.getMargin()+"%)");
                actualCapitalRequiredTv.setText("\u20B9 "+FormatUtils.addCommasInNumber(positionSizeDetail.getActualCapitalRequired()));

                //Set Inputs Layout -- GREEN / RED
                if(tradeDetailPOJO.isBuy()){
                    inputsLayout.setBackground(ContextCompat.getDrawable(activity.getApplicationContext(), R.drawable.edit_text_green_bg));
                }else{
                    inputsLayout.setBackground(ContextCompat.getDrawable(activity.getApplicationContext(), R.drawable.edit_text_red_bg));
                }
                setPadding(inputsLayout);

                //SET CHARGES
                setCharges(tradeDetailPOJO.getEntryPrice(), tradeDetailPOJO.getExitPrice(), tradeDetailPOJO.getStoploss(), positionSizeDetail.getQuantity());

            }
        }catch (Exception e){
            e.printStackTrace();

        }
    }


    private void setCharges(double entry, double exit, double stoploss, int quantity) {

        //PROFIT
        double profitCharges = 0;
        CalculateCharges.GetIndividualCharges getIndividualCharges = null;

        CalculateCharges calculateCharges = new CalculateCharges();

        if(entry < exit){
            //BUY
            getIndividualCharges = calculateCharges.getZerodhaChargesIntraday(entry, exit, quantity);
            if(getIndividualCharges != null){
                profitCharges = getIndividualCharges.getTotal();
            }
        }else{
            //SELL
            getIndividualCharges = calculateCharges.getZerodhaChargesIntraday(exit, entry, quantity);
            if(getIndividualCharges != null){
                profitCharges = getIndividualCharges.getTotal();
            }
        }
        if(tradingCapitalData.getMargin() == 100){
            if(entry < exit){
                //BUY
                getIndividualCharges = calculateCharges.getZerodhaChargesDelivery(entry, exit, quantity);
                if(getIndividualCharges != null){
                    profitCharges = getIndividualCharges.getTotal();
                }
            }else{
                //SELL
                getIndividualCharges = calculateCharges.getZerodhaChargesDelivery(exit, entry, quantity);
                if(getIndividualCharges != null){
                    profitCharges = getIndividualCharges.getTotal();
                }
            }
        }
        if(getIndividualCharges != null){
            profitChargesObj = getIndividualCharges;
        }

        //LOSS
        double lossCharges = 0;
        CalculateCharges.GetIndividualCharges getIndividualChargesL = null;

        if(entry > stoploss){
            //BUY
            getIndividualChargesL = calculateCharges.getZerodhaChargesIntraday(entry, stoploss, quantity);
            if(getIndividualChargesL != null){
                lossCharges = getIndividualChargesL.getTotal();
            }
        }else{
            //SELL
            getIndividualChargesL = calculateCharges.getZerodhaChargesIntraday(stoploss, entry, quantity);
            if(getIndividualChargesL != null){
                lossCharges = getIndividualChargesL.getTotal();
            }
        }
        if(tradingCapitalData.getMargin() == 100){
            if(entry > stoploss){
                //BUY
                getIndividualChargesL = calculateCharges.getZerodhaChargesDelivery(entry, stoploss, quantity);
                if(getIndividualChargesL != null){
                    lossCharges = getIndividualChargesL.getTotal();
                }
            }else{
                //SELL
                getIndividualChargesL = calculateCharges.getZerodhaChargesDelivery(stoploss, entry, quantity);
                if(getIndividualChargesL != null){
                    lossCharges = getIndividualChargesL.getTotal();
                }
            }
        }

        if(getIndividualChargesL != null){
            lossChargesObj = getIndividualChargesL;
        }

        //Set Views
        profitChargesTv.setText("\u20B9 "+profitCharges);
        lossChargesTv.setText("\u20B9 "+lossCharges);
    }

    public void setPadding(RelativeLayout relativeLayout){
        float density = activity.getApplicationContext().getResources().getDisplayMetrics().density;
        int paddingPixel = (int)(15 * density);
        relativeLayout.setPadding(paddingPixel, paddingPixel, paddingPixel, paddingPixel);
    }

}
