package com.example.money_management.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.money_management.R;
import com.example.money_management.activities.ForgetPasswordActivity;
import com.example.money_management.activities.LoginActivity;

import es.dmoral.toasty.Toasty;


public class FragmentOpenGmail extends Fragment {

        private View view;
        private Button button_openGmailapp;
        private TextView textview_openlogin;
        private TextView textview_openSendemail;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            view = inflater.inflate(R.layout.fragment_open_gmail, container, false);
            anhxa();

            button_openGmailapp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openGmail();
                }
            });

            textview_openlogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                    getActivity().overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                }
            });

            textview_openSendemail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ForgetPasswordActivity activity =  (ForgetPasswordActivity)getActivity();

                    if(activity != null)
                        activity.openSendemailFragment();
                }
            });

            return view;
        }
        private void openGmail() {
            Intent intent = null;
            ForgetPasswordActivity activity = (ForgetPasswordActivity) getActivity();
            try {
                intent = getActivity()
                        .getPackageManager().getLaunchIntentForPackage("com.google.android.gm");
            }catch (Exception e)
            {
                Toasty.error(activity.getApplicationContext(), "Xin lỗi, chúng tôi không thể mở  ứng dụng Gmail của bạn", Toast.LENGTH_LONG).show();
            }

            if(intent != null)
            {
                startActivity(intent);
            }else
            {
                Toasty.error(activity.getApplicationContext(), "Xin lỗi, chúng tôi không thể mở  ứng dụng Gmail của bạn", Toast.LENGTH_LONG).show();
            }
        }

        private void anhxa() {
            button_openGmailapp = view.findViewById(R.id.button_openGmailapp_opengmail);
            textview_openlogin = view.findViewById(R.id.textview_openlogin_opengmail);
            textview_openSendemail = view.findViewById(R.id.textview_openSendemail_opengmail);
        }
}
