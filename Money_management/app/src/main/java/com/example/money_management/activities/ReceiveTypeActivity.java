package com.example.money_management.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.money_management.R;

public class ReceiveTypeActivity extends AppCompatActivity {
    private ImageView btnBack;
    private CardView btnAdd, btnSalary, btnHotHui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_type);
        mapping();
        Log.i("Tracking Activity Created", "ReceiveTypeActivity");
        btnHotHui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btnSalary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddTypeActivity.class));
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
        btnBack = findViewById(R.id.button_back);
        btnSalary = findViewById(R.id.button_salary);
        btnHotHui = findViewById(R.id.button_hot_hui);
        btnAdd = findViewById(R.id.button_add);
    }
}