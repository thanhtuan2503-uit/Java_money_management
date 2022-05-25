package com.example.money_management;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.money_management.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final int FRAGMENT_TRANSACTION=1;
    private static final int FRAGMENT_REPORT=2;
    private static final int FRAGMENT_NOTE=3;
    private static final int FRAGMENT_ACCOUNT=4;

    private int current_Fragment=FRAGMENT_TRANSACTION;
    private BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationView = findViewById(R.id.bottomNavigationView);
    }
}