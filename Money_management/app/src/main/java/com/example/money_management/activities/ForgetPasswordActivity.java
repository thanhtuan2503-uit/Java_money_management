package com.example.money_management.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.money_management.fragments.FragmentSendEmail;
import com.example.money_management.R;
import com.example.money_management.fragments.FragmentOpenGmail;

public class ForgetPasswordActivity extends AppCompatActivity {

    private static final int FRAGMENT_OPEN_GMAIL = 2;
    private static final int FRAGMENT_SENDEMAIL = 1;

    private Fragment fragment;
    private int current_Fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        current_Fragment = 0;
        openSendemailFragment();
    }

    @Override
    public void onBackPressed() {
        if(current_Fragment == 1)
        {
            startActivity(new Intent(ForgetPasswordActivity.this, LoginActivity.class));
            super.onBackPressed();
            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
        }
        else if(current_Fragment == 2)
        {
            openSendemailFragment();
        }
    }


    public void openSendemailFragment()
    {
        if(current_Fragment == FRAGMENT_OPEN_GMAIL)
        {
            fragment = new FragmentSendEmail();
            replaceFragment(fragment);
            current_Fragment = FRAGMENT_SENDEMAIL;

        }
        else if(current_Fragment == 0)
        {
            addFragment(new FragmentSendEmail());
            current_Fragment = FRAGMENT_SENDEMAIL;
        }
    }

    public void openOpengmailFragment()
    {
        if(current_Fragment == FRAGMENT_SENDEMAIL)
        {
            fragment = new FragmentOpenGmail();
            replaceFragment(fragment);
            current_Fragment = FRAGMENT_OPEN_GMAIL;

        }
        else if(current_Fragment == 0)
        {
            addFragment(new FragmentOpenGmail());
            current_Fragment = FRAGMENT_OPEN_GMAIL;
        }
    }

    private void replaceFragment(Fragment fragment)
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.framelayout_forgetpassword,fragment);
        fragmentTransaction.commit();
    }

    private void addFragment(Fragment fragment)
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.framelayout_forgetpassword,fragment);
        fragmentTransaction.commit();
    }
}