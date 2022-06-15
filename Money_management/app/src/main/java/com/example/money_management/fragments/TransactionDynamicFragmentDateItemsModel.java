package com.example.money_management.fragments;

public class TransactionDynamicFragmentDateItemsModel {
    public Float Amount;
    public String TypeName;
    public String Icon;
    public String Note;
    public String Email;
    public String Type;
    public String Date;
    public String Limit;
    public String Source;
    public String ID;
    public TransactionDynamicFragmentDateItemsModel(Float amount, String typeName, String icon, String note, String date, String email, String type, String source, String id){
        Amount = amount;
        TypeName = typeName;
        Icon = icon;
        Note = note;
        Date = date;
        Email = email;
        Type = type;
        Source = source;
        ID = id;
    }

    public TransactionDynamicFragmentDateItemsModel(){

    }
}
