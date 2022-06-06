package com.example.money_management.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.money_management.R;
import com.google.android.material.textview.MaterialTextView;

public class TransactionAddActivity extends AppCompatActivity {
    private ImageView btnClose;
    private TextView btnSave;
    private MaterialTextView btnChooseSpending;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_add);
        mapping();
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btnChooseSpending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ChooseSpendingActivity.class));
            }
        });
    }

    //Ánh xạ
    private void mapping(){
        btnClose = findViewById(R.id.button_close);
        btnSave = findViewById(R.id.button_save);
        btnChooseSpending = findViewById(R.id.txt_choose_spending);
    }
}