package com.example.money_management.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.money_management.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText editTextEmail;
    private TextInputEditText editTextPassword;
    private TextView txtViewRegister;
    private MaterialTextView txtViewForgotPassword;
    private MaterialButton btnLogin;
    private ImageView btnFacebook, btnGoogle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mapping();
      
        txtViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });

        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    private void mapping() {
        editTextEmail = findViewById(R.id.editext_email_LogActivity);
        editTextPassword = findViewById(R.id.edittext_password_LogActivity);
        txtViewRegister = findViewById(R.id.textView_register_logActivity);
        txtViewForgotPassword = findViewById(R.id.txt_forgotpassword);
        btnLogin = findViewById(R.id.btn_login_LogActivity);
        btnFacebook = findViewById(R.id.button_facebook);
        btnGoogle = findViewById(R.id.button_google);
    }
}