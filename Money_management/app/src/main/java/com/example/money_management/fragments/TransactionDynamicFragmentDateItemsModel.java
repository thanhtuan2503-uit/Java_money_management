package com.example.money_management.fragments;

public class TransactionDynamicFragmentDateItemsModel {
    public Float Amount;
    public String TypeName;
    public String Icon;
    public String Note;
    public TransactionDynamicFragmentDateItemsModel(Float amount, String typeName, String icon, String note){
        Amount = amount;
        TypeName = typeName;
        Icon = icon;
        Note = note;
    }
}
