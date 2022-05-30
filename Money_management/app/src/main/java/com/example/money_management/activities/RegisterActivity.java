package com.example.money_management.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.money_management.R;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

public class RegisterActivity extends AppCompatActivity {

    LottieAnimationView lottieAnimationView;
    EditText editText_email;
    EditText editText_password;
    EditText editText_confirmpassword;
    Button button_register;
    TextView textView_signin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        anhxa();
        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        textView_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void anhxa() {
        textView_signin = findViewById(R.id.textView_signin);
        editText_email = findViewById(R.id.editext_email);
        editText_password = findViewById(R.id.edittext_password);
        editText_confirmpassword = findViewById(R.id.edittext_confirmpassword);
        button_register = findViewById(R.id.btn_register);
        lottieAnimationView = findViewById(R.id.LottieAnimationView_Reg);
    }
}