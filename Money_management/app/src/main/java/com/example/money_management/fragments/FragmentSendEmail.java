package com.example.money_management.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.money_management.R;
import com.example.money_management.activities.ForgetPasswordActivity;
import com.example.money_management.activities.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import es.dmoral.toasty.Toasty;

public class FragmentSendEmail extends Fragment {

    private TextInputEditText textInput_email;
    private TextView textview_login;
    private MaterialButton button_next;
    private View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_send_email, container, false);
        anhxa();

        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });

        textview_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });


        return view;
    }

    private boolean sendEmail() {

        String email = textInput_email.getText().toString();
        boolean b = validateData(email);
        ForgetPasswordActivity activity = (ForgetPasswordActivity) getActivity();
        if (b && activity != null) {
            FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toasty.success(activity.getApplicationContext(), "Vui l??ng ki???m tra email c???a b???n!", Toast.LENGTH_SHORT).show();
                        activity.openOpengmailFragment();
                    } else {
                        Toasty.error(activity.getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

        return b;
    }

    private boolean validateData(String email) {
        if (email.isEmpty()) {
            textInput_email.setError("Vui l??ng nh???p email!");
            return false;
        } else {
            return true;
        }
    }


    private void anhxa() {
        button_next = view.findViewById(R.id.button_send_forgotpassword);
        textInput_email = view.findViewById(R.id.textInput_email_forgotpassword);
        textview_login = view.findViewById(R.id.textview_login_forgetpassword);

    }
}
