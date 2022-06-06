package com.example.money_management.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.money_management.R;

public class SpendTypeActivity extends AppCompatActivity {
    private ImageView btnBack;
    private CardView btnAdd, btnFood, btnMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spend_type);
        mapping();

        btnFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btnMovie.setOnClickListener(new View.OnClickListener() {
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
        btnFood = findViewById(R.id.button_food);
        btnMovie = findViewById(R.id.button_movie);
        btnAdd = findViewById(R.id.button_add);
    }
}