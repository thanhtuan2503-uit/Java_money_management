package com.example.money_management.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;

import com.example.money_management.R;

public class RemindAddTransactionActivity extends AppCompatActivity {
    private ImageView btnBack;
    private Switch switch0, switch1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remind__add_transaction);
        mapping();
        Log.i("Tracking Activity Created", "RemindAddTransactionActivity");
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RemindAddTransactionActivity.this, SettingActivity.class));
                finish();
            }
        });
        switch0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        switch1.setOnClickListener(new View.OnClickListener() {
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
    private void mapping() {
        btnBack = findViewById(R.id.button_back);
        switch0 = findViewById(R.id.switch0);
        switch1 = findViewById(R.id.switch1);
    }
}