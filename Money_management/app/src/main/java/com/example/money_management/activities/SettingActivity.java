package com.example.money_management.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.money_management.R;

public class SettingActivity extends AppCompatActivity {
    private ImageView btnBack;
    private CardView btnDisplayMoney, btnDisplayDate, btnRemind, btnSynchronization;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mapping();
        Log.i("Tracking Activity Created", "SettingActivity");

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnDisplayMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),DisplayMoneyActivity.class));
                finish();
            }
        });

        btnDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), DisplayDateActivity.class));
                finish();
            }
        });

        btnRemind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RemindAddTransactionActivity.class));
                finish();
            }
        });

        btnSynchronization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
    private void mapping(){
        btnBack = findViewById(R.id.button_back);
        btnDisplayMoney = findViewById(R.id.button_displaymoney);
        btnDisplayDate = findViewById(R.id.button_displaydate);
        btnRemind = findViewById(R.id.button_remind);
        btnSynchronization = findViewById(R.id.button_synchronization);
    }
}