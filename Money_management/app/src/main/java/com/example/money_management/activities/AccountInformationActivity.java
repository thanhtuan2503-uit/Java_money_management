package com.example.money_management.activities;

import android.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.money_management.R;
import com.example.money_management.fragments.AccountFragment;

import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class AccountInformationActivity extends AppCompatActivity {
    AccountFragment accountFragment = new AccountFragment();
    private CardView btnChangeName, btnChangePassword, btnLogout;
    private ImageView btnBack;
    private SharedPreferences sharedpreferences; // Để thay đổi dữ trạng thái đăng nhập.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_information);
        mapping();
        sharedpreferences = getSharedPreferences("LoginPreferences", Context.MODE_PRIVATE);

        btnChangeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ChangeNameActivity.class));
                finish();
            }
        });

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ChangePasswordActivity.class));
                finish();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedpreferences.edit(); // Lưu trạng thái đăng nhập
                editor.putString("Email", "");
                editor.commit();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });

    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }
    private void mapping(){
        btnChangeName = findViewById(R.id.button_changeName);
        btnChangePassword = findViewById(R.id.button_change_password);
        btnLogout = findViewById(R.id.button_logout);
        btnBack =  findViewById(R.id.button_back);
    }


}