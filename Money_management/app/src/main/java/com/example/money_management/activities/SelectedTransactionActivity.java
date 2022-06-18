package com.example.money_management.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.money_management.R;
import com.example.money_management.fragments.TransactionDynamicFragmentDateItemsModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SelectedTransactionActivity extends AppCompatActivity {
    private ImageView btnClose, btnRepair, btnRemove;
    private TextView txtTypeName;
    private TextView txtAmount;
    private TextView txtDate;
    private TextView txtMoneySource;
    private TextView txtLimit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_transaction);
        mapping();
        Log.i("Tracking Activity Created", "SelectedTransactionActivity");

        SharedPreferences prefs = getSharedPreferences("TransactionDetails", MODE_PRIVATE);
        TransactionDynamicFragmentDateItemsModel childModel = new TransactionDynamicFragmentDateItemsModel();
        String email = prefs.getString("Email", "");
        String type = prefs.getString("Type", "");
        String typeName = prefs.getString("TypeName", "");
        String date = prefs.getString("Date", "");
        String amount = prefs.getString("Amount", "");
        String amountHD = prefs.getString("AmountHD", "");
        String source = prefs.getString("Source", "");
        String limit = prefs.getString("Limit", "");
        String id = prefs.getString("ID", "");
        String note = prefs.getString("Note", "");
        childModel.Email = email;
        childModel.Type = type;
        childModel.TypeName = typeName;
        childModel.Date = date;
        childModel.Amount = Integer.valueOf((amountHD));
        childModel.ID = id;
        childModel.Note = note;
        String2Currency convert = new String2Currency();
        txtDate.setText(getDayOfWeek(date) + " " + date);
        txtAmount.setText(convert.convertString2Currency(amountHD));
        txtTypeName.setText(typeName);
        txtLimit.setText(limit);
        txtMoneySource.setText(note);
        Log.i("Note", note);
        if(type.equals("Chi")) {
            txtAmount.setTextColor(Color.rgb(246, 0, 0));
            txtAmount.setText(convert.convertString2Currency((amountHD)).substring(1));
        }
        else {
            txtAmount.setTextColor(Color.rgb(0, 178, 18));
            txtAmount.setHintTextColor(Color.rgb(0, 178, 18));
        }

        //Xóa giao dịch đã chọn.
        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItem(childModel);
            }
        });

        // Sửa đổi giao dịch đã chọn.
        btnRepair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), DetailTransactionActivity.class));
            }
        });

        // Bấm quay lại
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getNote(String email, String ID){

    }

    private void deleteItem(TransactionDynamicFragmentDateItemsModel childModel){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Transactions").document(childModel.ID)
                .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                });
    }

    //ánh xạ
    private void mapping() {
        btnClose = findViewById(R.id.button_close);
        btnRepair = findViewById(R.id.btn_Edit_Selected);
        btnRemove = findViewById(R.id.btn_Delete_Selected);
        txtTypeName = findViewById(R.id.selected_TypeName);
        txtAmount = findViewById(R.id.selected_Amount);
        txtDate = findViewById(R.id.selected_Date);
        txtMoneySource = findViewById(R.id.selected_Note);
        txtLimit = findViewById(R.id.selected_Limit);
    }

    public String getDayOfWeek(String date){
        if(date == null)
            return "";

        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
        Date dt1 = null;
        try {
            dt1 = format1.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat format2 = new SimpleDateFormat("EEEE");
        String finalDay = format2.format(dt1);
        return finalDay;
    }
}