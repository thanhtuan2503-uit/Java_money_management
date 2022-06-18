package com.example.money_management.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.money_management.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class NoteFragment extends Fragment {

    private View mView;
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private FragmentNoteAdapter fragmentNoteApdapter;
    private String[] tabName={"Thu", "Chi"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mView= inflater.inflate(R.layout.fragment_note, container, false);
        mapping();
        fragmentNoteApdapter = new FragmentNoteAdapter(this);
        viewPager2.setAdapter(fragmentNoteApdapter);
        new TabLayoutMediator(
                tabLayout,
                viewPager2,
                (tab, position) -> {
                    tab.setText(tabName[position]);

                }
        ).attach();
        return mView;
    }

    private void mapping() {
        tabLayout = mView.findViewById(R.id.tab_layout);
        viewPager2 = mView.findViewById(R.id.view_paper2);
    }
}