package com.example.money_management.fragments;

public class TransactionModel {
    public String Email;
    public String Date;
    public String Note;
    public Integer Amount;
    public String Source;
    public String Type;
    public String TypeName;
    public String ID;
    public TransactionModel(String email, String date, String note, Integer amount, String source, String type, String typeName, String id){
        Email = email;
        Date = date;
        Note = note;
        Amount = amount;
        Source = source;
        Type = type;
        TypeName = typeName;
        ID = id;
    }
}
