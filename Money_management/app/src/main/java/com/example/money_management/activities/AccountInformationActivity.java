package com.example.money_management.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
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

            }
        });

    }
    public boolean popFragment() {
        boolean isPop = false;
        Fragment currentFragment = getSupportFragmentManager()
                .findFragmentById(R.id.transaction);
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            isPop = true;
            getSupportFragmentManager().popBackStackImmediate();
        }
        return isPop;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!popFragment()) {
            finish();
        }
    }
    private void mapping(){
        btnChangeName = findViewById(R.id.button_changeName);
        btnChangePassword = findViewById(R.id.button_change_password);
        btnLogout = findViewById(R.id.button_logout);
        btnBack =  findViewById(R.id.button_back);
    }


}