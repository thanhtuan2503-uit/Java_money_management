package com.example.money_management.fragments;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TransactionDynamicFragmentDatesModel {
    public String Date;
    public Integer Amount;
    public ArrayList<TransactionDynamicFragmentDateItemsModel> childList;

    public TransactionDynamicFragmentDatesModel(String date, Integer amount, ArrayList<TransactionDynamicFragmentDateItemsModel> childlist){
        Date = date;
        Amount = amount;
        childList = childlist;
    }

    public TransactionDynamicFragmentDatesModel(){

    }

    public ArrayList<TransactionDynamicFragmentDateItemsModel> getChildList(){
        return childList;
    }

    public void setChildList(ArrayList<TransactionDynamicFragmentDateItemsModel> childlist){
        childList = childlist;
    }
}
