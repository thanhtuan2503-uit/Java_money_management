package com.example.money_management.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.money_management.R;
import com.google.android.material.textfield.TextInputEditText;

public class ChangePasswordActivity extends AppCompatActivity {
    private ImageView btnBack;
    private AppCompatButton btnChangePassword;
    private TextView btnForgotPassword;
    private TextInputEditText txtCurrentPassword, txtNewPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        mapping();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChangePasswordActivity.this,AccountInformationActivity.class));
                finish();
            }
        });

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enteredNewPassword = txtNewPassword.getText().toString();
                String enteredCurrentPassword = txtCurrentPassword.getText().toString();
                if (enteredNewPassword != enteredCurrentPassword) {
                    Toast.makeText(getApplicationContext(), "Mật khẩu không khớp! Vui lòng nhập lại", Toast.LENGTH_SHORT).show();
                }
                else if(enteredNewPassword.isEmpty() || enteredCurrentPassword.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập lại mật khẩu", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), AccountInformationActivity.class));
        finish();
    }

    private void mapping(){
        btnBack = findViewById(R.id.button_back);
        btnChangePassword = findViewById(R.id.button_change_password);
        btnForgotPassword = findViewById(R.id.forgot_password);
        txtCurrentPassword = findViewById(R.id.txt_current_password);
        txtNewPassword = findViewById(R.id.txt_new_password);
    }
}