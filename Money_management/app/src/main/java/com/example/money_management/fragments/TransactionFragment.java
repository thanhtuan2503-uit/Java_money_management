package com.example.money_management.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.money_management.R;
import com.example.money_management.activities.SpendTypeActivity;
import com.example.money_management.activities.String2Currency;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class TransactionFragment extends Fragment {
    private View mView;
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private TextView txtMoneySourceAmount;
    private TabItem tab1, tab2, tab3, tab4, tab5, tab6, tab7, tab8, tab9, tab10, tab11, tab12;
    private SharedPreferences sharedpreferences;
    private String[] tabName={"1/2021", "2/2021","3/2021","4/2021","5/2021","6/2021","7/2021","8/2021","9/2021","10/2021","11/2021","12/2021",
                              "1/2022", "2/2022","3/2022","4/2022","5/2022","6/2022","7/2022","8/2022","9/2022","10/2022","11/2022","12/2022",
                              "1/2023", "2/2023","3/2023","4/2023","5/2023","6/2023","7/2023","8/2023","9/2023","10/2023","11/2023","12/2023"};
    private TransactionDynamicFragment fragment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_transaction, container, false);
        mapping();
        sharedpreferences = mView.getContext().getSharedPreferences("TransactionIndexPage", Context.MODE_PRIVATE);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                saveIndex(tab.getPosition());
                viewPager2.setCurrentItem(tab.getPosition(), false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        viewPager2.registerOnPageChangeCallback (new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                saveIndex(position);
                tabLayout.setScrollPosition(position,0f,true);
            }

            @Override
            public void onPageSelected(int position) {

            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        return mView;
    }

    public void saveIndex(Integer position){
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt("Index", position);
        editor.commit();
        Integer index = sharedpreferences.getInt("Index", 15);
        Log.i("Position", String.valueOf(position) + " " + String.valueOf(index));
    }

    public void onViewCreated(View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        setDynamicFragmentToTabLayout();
        updateMoneyAmount();
    }

    private void setDynamicFragmentToTabLayout() {
        for (int i = 0; i < 36; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(tabName[i]));
        }
        FragmentTransactionAdapter mDynamicFragmentAdapter = new FragmentTransactionAdapter(getChildFragmentManager(),getLifecycle(), tabLayout.getTabCount());
        viewPager2.setAdapter(mDynamicFragmentAdapter);

        Integer index = sharedpreferences.getInt("Index", 15);
        Log.i("Position aaaaa", String.valueOf(index));
        viewPager2.setCurrentItem(16, false);
    }

    private void mapping() {
        tabLayout = mView.findViewById(R.id.tab_layout);
        viewPager2 = mView.findViewById(R.id.view_paper2);
        txtMoneySourceAmount = mView.findViewById(R.id.txt_display_money);
    }

    public void updateMoneyAmount(){
        sharedpreferences = mView.getContext().getSharedPreferences("LoginPreferences", Context.MODE_PRIVATE); // Chọn file có tên "LoginPreferences"
        String logged_Email = sharedpreferences.getString("Email", "");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("MoneySource")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("TransactionFragment", "Tag Data" + document.getId() + " => " + document.getData());
                                String email = document.getString("Email");
                                if(email.equals(logged_Email)){
                                    String amount = (document.getString("Amount"));
                                    String2Currency convert = new String2Currency();
                                    Log.i("Tiền mặt trên db", amount);
                                    txtMoneySourceAmount.setText(convert.convertString2Currency(amount));
                                    break;
                                }
                            }
                        } else {
                            Log.w("TransactionFragment", "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}