package com.example.money_management.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.money_management.R;
import com.google.android.material.textview.MaterialTextView;

public class AddTypeActivity extends AppCompatActivity {
    private MaterialTextView btnImage;
    private ImageView btnBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_type);
        mapping();
        btnImage.setOnClickListener(new View.OnClickListener() {
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

    //ánh xạ
    private void mapping() {
        btnImage = findViewById(R.id.txt_choose_image);
        btnBack = findViewById(R.id.button_back);
    }
}