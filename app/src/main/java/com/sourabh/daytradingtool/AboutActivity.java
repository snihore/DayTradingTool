package com.sourabh.daytradingtool;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sourabh.daytradingtool.UserInterface.HelpRecyclerViewAdaper;

public class AboutActivity extends AppCompatActivity {

    private ImageView backBtn;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        backBtn = (ImageView) findViewById(R.id.help_back_btn);
        recyclerView = (RecyclerView) findViewById(R.id.help_recycler_view);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        recyclerView.setAdapter(new HelpRecyclerViewAdaper());
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


    }




}