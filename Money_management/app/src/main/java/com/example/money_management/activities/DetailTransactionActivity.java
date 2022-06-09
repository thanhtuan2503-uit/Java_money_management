package com.example.money_management.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.money_management.R;

public class DetailTransactionActivity extends AppCompatActivity {
    private ImageView btnBack;
    private TextView btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_transaction);
        mapping();
        Log.i("Tracking Activity Created", "DetailTransactionActivity");

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void mapping() {
        btnBack = findViewById(R.id.button_back);
        btnSave = findViewById(R.id.button_save);
    }
}