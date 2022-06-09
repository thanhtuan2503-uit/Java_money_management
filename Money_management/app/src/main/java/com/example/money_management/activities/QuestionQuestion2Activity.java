package com.example.money_management.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.money_management.R;

public class QuestionQuestion2Activity extends AppCompatActivity {
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_question2);
        mapping();
        Log.i("Tracking Activity Created", "QuestionQuestion2Activity");
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QuestionQuestion2Activity.this, QuestionActivity.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), QuestionActivity.class));
        finish();
    }
    private void mapping(){
        btnBack = findViewById(R.id.button_back);
    }
}