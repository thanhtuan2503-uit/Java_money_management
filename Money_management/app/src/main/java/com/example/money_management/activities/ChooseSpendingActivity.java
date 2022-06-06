package com.example.money_management.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.money_management.R;

public class ChooseSpendingActivity extends AppCompatActivity {
    private CardView btnTypesOfTaxes;
    private CardView btnTypesOfGenera;
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_spending);
        mapping();

        btnTypesOfTaxes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ReceiveTypeActivity.class));

            }
        });
        btnTypesOfGenera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SpendTypeActivity.class));
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    //ánh xạ
    private void mapping(){
        btnTypesOfTaxes = findViewById(R.id.button_types_of_taxes);
        btnTypesOfGenera = findViewById(R.id.button_types_of_genera);
        btnBack = findViewById(R.id.button_back);
    }
}