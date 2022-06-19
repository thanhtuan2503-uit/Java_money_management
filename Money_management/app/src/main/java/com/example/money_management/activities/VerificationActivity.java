package com.example.money_management.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.money_management.R;
import com.google.android.material.button.MaterialButton;

public class VerificationActivity extends AppCompatActivity {
    private MaterialButton button_gotoLogin;
    private MaterialButton button_backtoRegister;
    private SharedPreferences sharedpreferences; // Để thay đổi dữ trạng thái đăng nhập.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        anhxa();
        sharedpreferences = getSharedPreferences("LoginPreferences", Context.MODE_PRIVATE); // Chọn file có tên "LoginPreferences"
        String logged_Email = sharedpreferences.getString("Email", "");


        button_gotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit(); // Lưu trạng thái đăng nhập
                editor.putString("Email", "");
                editor.commit();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });

        button_backtoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
        finish();
    }

    private void anhxa() {
        button_gotoLogin = findViewById(R.id.button_gotoLogin_verify);
        button_backtoRegister = findViewById(R.id.button_backtoRegister_verify);
    }
}
