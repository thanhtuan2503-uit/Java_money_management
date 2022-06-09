package com.example.money_management.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.money_management.R;

public class ChooseSpendingActivity extends AppCompatActivity {
    private CardView btnTypesOfTaxes;
    private CardView btnTypesOfGenera;
    private ImageView btnBack;
    private SharedPreferences sharedpreferences; // Lấy dữ liệu đã chọn

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_spending);
        mapping();
        Log.i("Tracking Activity Created", "ChooseSpendingActivity");

        btnTypesOfTaxes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedpreferences = view.getContext().getSharedPreferences("Selected Transaction Pre-Type", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("Selected Transaction Pre-Type", "Red");
                editor.commit();
                startActivity(new Intent(getApplicationContext(), ReceiveTypeActivity.class));

            }
        });
        btnTypesOfGenera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedpreferences = view.getContext().getSharedPreferences("Selected Transaction Type", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("Selected Transaction Type", "Green");
                editor.commit();
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