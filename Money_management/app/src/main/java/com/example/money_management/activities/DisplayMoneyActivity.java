package com.example.money_management.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;

import com.example.money_management.R;

public class DisplayMoneyActivity extends AppCompatActivity {
    private ImageView btnBack;
    private Switch switch1, switch2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_money);
        mapping();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DisplayMoneyActivity.this,SettingActivity.class));
                finish();
            }
        });

        switch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        switch2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), SettingActivity.class));
        finish();
    }
    private void mapping(){
        btnBack = findViewById(R.id.button_back);
        switch1 = findViewById(R.id.switch1);
        switch2 = findViewById(R.id.switch2);
    }
}