package com.example.money_management.fragments;

public class TransactionModel {
    public String Email;
    public String Date;
    public String Note;
    public Float Amount;
    public String Source;
    public String Type;
    public String TypeName;
    public TransactionModel(String email, String date, String note, Float amount, String source, String type, String typeName){
        Email = email;
        Date = date;
        Note = note;
        Amount = amount;
        Source = source;
        Type = type;
        TypeName = typeName;
    }
}
