package com.example.money_management.activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.money_management.R;
import com.example.money_management.fragments.AccountFragment;
import com.example.money_management.fragments.NoteFragment;
import com.example.money_management.fragments.ReportFragment;
import com.example.money_management.fragments.TransactionFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.money_management.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    AccountFragment accountFragment = new AccountFragment();
    NoteFragment noteFragment= new NoteFragment();
    TransactionFragment transactionFragment = new TransactionFragment();
    ReportFragment reportFragment = new ReportFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,transactionFragment).commit();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.transaction:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,transactionFragment).commit();
                        return true;
                    case R.id.account:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,accountFragment).commit();
                        return true;
                    case R.id.note:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,noteFragment).commit();
                        return true;
                    case R.id.report:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,reportFragment).commit();
                        return true;
                }
                return false;
            }
        });
    }
}