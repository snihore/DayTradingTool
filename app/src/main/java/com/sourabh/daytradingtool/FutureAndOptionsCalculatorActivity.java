package com.sourabh.daytradingtool;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sourabh.daytradingtool.Data.TradingCapitalData;
import com.sourabh.daytradingtool.Database.TradingCapitalDetailDB;
import com.sourabh.daytradingtool.UserInterface.FutureAndOptionsRecyclerViewAdaper;
import com.sourabh.daytradingtool.UserInterface.HelpRecyclerViewAdaper;
import com.sourabh.daytradingtool.Utils.FormatUtils;

import java.util.ArrayList;
import java.util.HashMap;

public class FutureAndOptionsCalculatorActivity extends AppCompatActivity {

    private TradingCapitalData tradingCapitalData;
    private TextView tradingCapitalTv, riskPerTradeTv;
    private EditText lotSizeEt;
    private RecyclerView recyclerView;

    private ImageView backBtn;

    private ArrayList<HashMap> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_future_and_options_calculator);

        initViews();
    }

    private void initViews() {
        tradingCapitalTv = (TextView)findViewById(R.id.trading_capital_tv);
        riskPerTradeTv = (TextView)findViewById(R.id.risk_per_trade_tv);
        lotSizeEt = (EditText) findViewById(R.id.lot_size_et);
        recyclerView = (RecyclerView)findViewById(R.id.fno_recycler_view);
        backBtn = (ImageView)findViewById(R.id.fno_back_btn);


        try {
            setTradingCapitalDetail();


            backBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });

            lotSizeEt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    try {
                        double lotSize = Double.valueOf(charSequence.toString());
                        double rpt = tradingCapitalData.getRiskPerTrade();

                        arrayList.clear();

                        for (int j=1; j<=30; j++){

                            double slPoint = j;
                            double lots = 1;


                            while (rpt >= (lots * lotSize * slPoint)){
                                lots += 1;
                            }

                            HashMap<String, Double> hashMap = new HashMap<>();

                            hashMap.put("stoploss_points", slPoint);
                            hashMap.put("lots", lots-1);
                            hashMap.put("total_loss", (lots-1)*lotSize*slPoint);
                            hashMap.put("quantity", (lots-1)*lotSize);


                            arrayList.add(hashMap);

//                            Log.i(">>>>>>>>>>", "Stoploss Points: "+slPoint+", Number of Lots: "+(lots-1)+", Total Loss: "+((lots-1)*lotSize*slPoint));
                        }

                        // Set Recycler View
                        recyclerView.setAdapter(new FutureAndOptionsRecyclerViewAdaper(arrayList));
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                    }catch (Exception e){
                        e.printStackTrace();

                        arrayList.clear();

                        // Set Recycler View
                        recyclerView.setAdapter(new FutureAndOptionsRecyclerViewAdaper(arrayList));
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setTradingCapitalDetail() {

        try{

            TradingCapitalDetailDB tradingCapitalDetailDB = new TradingCapitalDetailDB(this);

            if(tradingCapitalDetailDB == null){

                return;
            }

            tradingCapitalData = tradingCapitalDetailDB.getTradingCapitalDetail();

            if(tradingCapitalData == null){

                return;
            }

            if(tradingCapitalData.getTradingCapital() == 0 || tradingCapitalData.getRiskPerTrade() == 0 || tradingCapitalData.getMargin() == 0){

            }

            tradingCapitalTv.setText("\u20B9 "+ FormatUtils.addCommasInNumber(tradingCapitalData.getTradingCapital())+" ");
            riskPerTradeTv.setText("\u20B9 "+FormatUtils.addCommasInNumber(tradingCapitalData.getRiskPerTrade())+" ");


        }catch (Exception e){
            e.printStackTrace();
        }


    }
}