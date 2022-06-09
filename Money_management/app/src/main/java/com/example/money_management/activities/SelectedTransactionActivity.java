package com.example.money_management.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.money_management.R;

public class SelectedTransactionActivity extends AppCompatActivity {
    private ImageView btnClose, btnRepair, btnRemove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_transaction);
        mapping();
        Log.i("Tracking Activity Created", "SelectedTransactionActivity");

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btnRepair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), DetailTransactionActivity.class));
            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //ánh xạ
    private void mapping() {
        btnClose = findViewById(R.id.button_close);
        btnRepair = findViewById(R.id.button_repair);
        btnRemove = findViewById(R.id.button_remove);
    }
}