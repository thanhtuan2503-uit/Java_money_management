package com.example.money_management.fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class FragmentNoteAdapter extends FragmentStateAdapter {
    public FragmentNoteAdapter(@NonNull NoteFragment fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0: return new NoteDynamicFragment();
            case 1: return new Note2DynamicFragment();
        }
        return new NoteDynamicFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
