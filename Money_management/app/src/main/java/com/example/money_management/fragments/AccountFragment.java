package com.example.money_management.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.money_management.activities.IntroductionActivity;
import com.example.money_management.activities.MainActivity;
import com.example.money_management.activities.QuestionActivity;
import com.example.money_management.R;
import com.example.money_management.activities.SettingActivity;
import com.example.money_management.activities.AccountInformationActivity;

public class AccountFragment extends Fragment {
    private View mView;
    private CardView btnAccountIn;
    private CardView btnSetting;
    private CardView btnIntroduction;
    private CardView btnQuestion;
    private CardView btnLogout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mView= inflater.inflate(R.layout.fragment_account, container, false);
        mapping();

        btnAccountIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),AccountInformationActivity.class));
                getActivity().finish();
            }
        });

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), SettingActivity.class));
                getActivity().finish();
            }
        });

        btnIntroduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), IntroductionActivity.class));
                getActivity().finish();
            }
        });

        btnQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), QuestionActivity.class));
                getActivity().finish();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return mView;
    }

    private void mapping(){
        btnAccountIn = mView.findViewById(R.id.button_accountInfor);
        btnSetting = mView.findViewById(R.id.button_setting);
        btnIntroduction = mView.findViewById(R.id.button_introduction);
        btnQuestion = mView.findViewById(R.id.button_question);
        btnLogout = mView.findViewById(R.id.button_logout);
    }
}