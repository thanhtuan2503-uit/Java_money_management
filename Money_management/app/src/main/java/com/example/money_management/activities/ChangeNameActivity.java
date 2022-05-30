package com.example.money_management.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.money_management.R;
import com.google.android.material.textfield.TextInputEditText;

public class ChangeNameActivity extends AppCompatActivity {
    private ImageView btnBack;
    private AppCompatButton btnChangeName;
    private TextInputEditText txtNewName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_name);
        mapping();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChangeNameActivity.this,AccountInformationActivity.class));
                finish();
            }
        });
        btnChangeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String enteredData = txtNewName.getText().toString();
                    if (enteredData.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Please Enter the Data", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), enteredData, Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception ex)
                {
                    ex.getMessage();
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),AccountInformationActivity.class));
        finish();
    }

    private void mapping(){
        btnBack = findViewById(R.id.button_back);
        btnChangeName = findViewById(R.id.button_change_name);
        txtNewName = findViewById(R.id.txt_new_name);
    }
}