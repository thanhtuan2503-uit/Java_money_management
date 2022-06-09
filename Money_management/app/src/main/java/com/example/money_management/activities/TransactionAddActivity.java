package com.example.money_management.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.money_management.R;
import com.google.android.material.textview.MaterialTextView;

public class TransactionAddActivity extends AppCompatActivity {
    private ImageView btnClose;
    private TextView btnSave;
    private MaterialTextView btnChooseSpending;
    private SharedPreferences sharedpreferences; // Lấy dữ liệu đã chọn
    private boolean Activity_First_Show = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_add);
        mapping();
        Log.i("Tracking Activity Created", "TransactionAddActivity");
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

    @Override
    public void onResume(){
        super.onResume();
        if(!Activity_First_Show){
            Activity_First_Show = true;
            return;
        }
        sharedpreferences = getSharedPreferences("Selected Transaction Type", Context.MODE_PRIVATE);
        btnChooseSpending.setText(sharedpreferences.getString("Selected Transaction Type", ""));
    }

    //Ánh xạ
    private void mapping(){
        btnClose = findViewById(R.id.button_close);
        btnSave = findViewById(R.id.button_save);
        btnChooseSpending = findViewById(R.id.txt_choose_spending);
    }
}