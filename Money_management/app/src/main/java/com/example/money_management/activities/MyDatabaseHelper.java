package com.example.money_management.activities;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "MoneyManagement.db";
    public static final String Account_Table_Name = "Account_Table";
    public static final String ID_COL = "ID";
    public static final String UserName_COL = "UserName";
    public static final String Password_COL = "Password";
    public static final String Email_COL = "Email";
    public static final String Name_COL = "Name";

    public MyDatabaseHelper(Context context){
        super(context, DATABASE_NAME,null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int NewVersion){

    }
}
