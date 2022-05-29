package com.example.money_management.activities;

import android.content.Intent;
import android.os.Bundle;

import com.example.money_management.R;
import com.example.money_management.fragments.AccountFragment;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.money_management.databinding.ActivityAccountInformationBinding;

public class AccountInformationActivity extends AppCompatActivity {
    private CardView btnChangeName, btnChangePassword, btnLogout;
    private ImageView btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_information);
        mapping();
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
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AccountFragment.class));
                finish();
            }
        });

    }
    private void mapping(){
        btnChangeName = findViewById(R.id.button_changeName);
        btnChangePassword = findViewById(R.id.button_change_password);
        btnLogout = findViewById(R.id.button_logout);
        btnBack =  findViewById(R.id.button_back);
    }


}