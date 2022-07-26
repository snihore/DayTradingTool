package com.sourabh.daytradingtool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class PositionSizeActivity extends AppCompatActivity {

    private ImageView tradeListBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position_size);

        initViews();
    }

    private void initViews() {
        tradeListBtn = (ImageView) findViewById(R.id.trade_list_btn);

        tradeListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TradeListActivity.class);
                startActivity(intent);
            }
        });
    }
}