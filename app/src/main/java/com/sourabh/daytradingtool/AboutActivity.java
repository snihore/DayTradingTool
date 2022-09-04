package com.sourabh.daytradingtool;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    private TextView version, description;
    private ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        initViews();
    }

    private void initViews() {
        version = (TextView) findViewById(R.id.about_version);

        description = (TextView) findViewById(R.id.about_description);

        backBtn = (ImageView) findViewById(R.id.about_back_btn);

        setViews();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void setViews() {

        try{

            version.setText(BuildConfig.VERSION_NAME);

            description.setText("Day Trading Tool provide you the necessary tools to develop your risk management skills for traders. Risk management consider to be one of the most important skills in trading. \n" +
                    "\n" +
                    "Trade using a state-of-the-art risk management system built into our app, offering a unique stop loss/book profit mechanism that actively manages your trading risks. It ensures that you never lose more than the risk level you set in a trade. And you see all the trading charges upfront!\n" +
                    "\n" +
                    "Essential Tool for Traders\n" +
                    "\n" +
                    "Day Trading Tool include:\n" +
                    "+Position Size\n" +
                    "+Total Loss & Take Profit\n" +
                    "+Risk Reward\n" +
                    "+Margin\n" +
                    "+Taxes and Charges\n" +
                    "\n" +
                    "\n" +
                    "Proper position sizing is the key to managing risk in trading. Position Size Calculator help you calculate the amount of units/shares to put on a single trade based on your risk percentage/amount and stop loss price.\n" +
                    "\n" +
                    "Provide the appropriate stop loss price and take profit price to calculate position size, total profit/loss, taxes & charges and risk reward ratio.\n" +
                    "\n" +
                    "Margin Calculate help you calculate margin requirements of a trade position based on the position size and the account leverage/margin.\n" +
                    "\n" +
                    "Use above tools to plan your trades and always trade with a plan, this will help you go a long way as a trader.\n" +
                    "\n" +
                    "If you have any questions or feedback please feel free to write in the comments or contact me through email.\n" +
                    "\n" +
                    "Please do not report issues or bugs via Play Store reviews. Instead, feel free to email us for support.\n" +
                    "\n" +
                    "CUSTOMER SUPPORT\n" +
                    "https://instagram.com/daytradingapp/ or e-mail at souravnihore1998@gmail.com\n" +
                    "\n" +
                    "Please don't contact support through reviews. \n" +
                    "It is humble request to report bugs, issues, or communicate questions over e-mail or Instagram page.\n" +
                    "\n" +
                    "☺ If you are happy with the app, do show your encouragement for us by reviewing the app with 5 stars.");

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}