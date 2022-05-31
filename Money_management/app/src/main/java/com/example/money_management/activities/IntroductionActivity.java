package com.example.money_management.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.money_management.R;
import com.example.money_management.fragments.AccountFragment;

public class IntroductionActivity extends AppCompatActivity {
    private ImageView btnBack;
    AccountFragment accountFragment = new AccountFragment();
    Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);
        mapping();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {


    }
    private void mapping(){
        btnBack = findViewById(R.id.button_back);
    }
}