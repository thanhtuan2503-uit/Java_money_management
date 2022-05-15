package com.example.money_management.ui.transaction;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TransactionViewModel extends androidx.lifecycle.ViewModel {

    private final MutableLiveData<String> mText;

    public TransactionViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is transaction fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}