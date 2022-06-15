package com.example.money_management.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.money_management.R;
import com.google.android.material.tabs.TabLayout;

public class TransactionFragment extends Fragment {
    private View mView;
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_transaction, container, false);
        mapping();
        FragmentManager manager1 = getChildFragmentManager();
        FragmentTransactionAdapter adapter = new FragmentTransactionAdapter(manager1, getLifecycle());
        viewPager2.setAdapter(adapter);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
        return mView;
    }

    private void mapping( ) {
        tabLayout = mView.findViewById(R.id.tab_layout);
        viewPager2 = mView.findViewById(R.id.view_paper2);
    }
}