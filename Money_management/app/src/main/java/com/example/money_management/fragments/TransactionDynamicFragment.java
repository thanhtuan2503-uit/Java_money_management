package com.example.money_management.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;

import com.example.money_management.R;
import com.example.money_management.activities.DetailTransactionActivity;
import com.example.money_management.activities.SelectedTransactionActivity;

public class TransactionDynamicFragment extends Fragment {
    private View mView;
    private TableRow btnChoose;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mView= inflater.inflate(R.layout.fragment_dynamic_transaction, container, false);
        mapping();
        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), SelectedTransactionActivity.class));
            }
        });
        return mView;
    }

    private void mapping() {
        btnChoose = mView.findViewById(R.id.btn_choose);
    }
}