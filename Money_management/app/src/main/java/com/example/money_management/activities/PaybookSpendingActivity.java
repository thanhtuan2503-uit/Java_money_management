package com.example.money_management.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.money_management.R;
import com.google.android.material.textview.MaterialTextView;

public class PaybookSpendingActivity extends AppCompatActivity {
    private MaterialTextView btnChooseTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paybook_spending);
        mapping();
        Log.i("Tracking Activity Created", "PaybookSpendingActivity");
        btnChooseTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), PaybookChooseActivity.class));
            }
        });
    }

    private void mapping() {
        btnChooseTransaction = findViewById(R.id.txt_choose_spending);
    }
}