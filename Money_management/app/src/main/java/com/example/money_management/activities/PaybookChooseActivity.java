package com.example.money_management.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.money_management.R;

public class PaybookChooseActivity extends AppCompatActivity {
    private ImageView btnBack;
    private CardView button_types_of_taxes, button_types_of_genera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paybook_choose);
        mapping();
        Log.i("Tracking Activity Created", "PaybookChooseActivity");
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        button_types_of_taxes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        button_types_of_genera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void mapping() {
        btnBack = findViewById(R.id.button_back);
        button_types_of_taxes = findViewById(R.id.button_types_of_taxes);
        button_types_of_genera = findViewById(R.id.button_types_of_genera);
    }
}