package com.example.money_management.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.money_management.R;

public class QuestionActivity extends AppCompatActivity {
    private ImageView btnBack;
    private CardView btnQuestion1, btnQuestion2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        mapping();

        btnQuestion1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), QuestionQuestion1Activity.class));
                finish();
            }
        });

        btnQuestion2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), QuestionQuestion2Activity.class));
                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
    private void mapping() {
        btnBack = findViewById(R.id.button_back);
        btnQuestion1 = findViewById(R.id.button_question1);
        btnQuestion2 = findViewById(R.id.button_question2);
    }
}