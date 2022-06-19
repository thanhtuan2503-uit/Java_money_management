package com.example.money_management.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class FragmentReportAdapter extends FragmentStateAdapter {
    public FragmentReportAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        ReportDynamicFragment fragment = new ReportDynamicFragment();
        Bundle args = new Bundle();
        args.putInt("Month", (position + 1) % 12);
        args.putInt("Year",  2022 + (position+1) / 12 - 1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 36;
    }
}
