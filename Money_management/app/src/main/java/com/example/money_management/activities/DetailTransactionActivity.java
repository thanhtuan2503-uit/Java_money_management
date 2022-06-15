package com.example.money_management.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.money_management.R;
import com.example.money_management.fragments.TransactionDynamicFragmentAdapterDateItemsAdapter;
import com.example.money_management.fragments.TransactionDynamicFragmentDateItemsModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DetailTransactionActivity extends AppCompatActivity {
    private ImageView btnClose;
    private TextView btnSave;
    private EditText editextMoney;
    private EditText editextDate;
    private EditText editextSource;
    private EditText editextLimit;
    private TextView editextTypeName;
    private TextInputLayout TextInputLayout;
    private String thisTag = "DetailTransactionActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_transaction);
        mapping();
        Log.i("Tracking Activity Created", "DetailTransactionActivity");

        SharedPreferences prefs = getSharedPreferences("TransactionDetails", MODE_PRIVATE);
        TransactionDynamicFragmentDateItemsModel childModel = new TransactionDynamicFragmentDateItemsModel();

        String email = prefs.getString("Email", "");
        String type = prefs.getString("Type", "");
        String typeName = prefs.getString("TypeName", "");
        String date = prefs.getString("Date", "");
        String amount = prefs.getString("Amount", "");
        String source = prefs.getString("Source", "");
        String limit = prefs.getString("Limit", "");
        String id = prefs.getString("ID", "");
        childModel.Email = email;
        childModel.Type = type;
        childModel.TypeName = typeName;
        childModel.Date = date;
        childModel.Amount = Float.valueOf(amount);
        childModel.ID = id;
        editextDate.setText(date);
        editextMoney.setText(amount);
        editextTypeName.setText(typeName);
        editextLimit.setText(limit);
        editextSource.setText(source);
        if(type.equals("Chi")) {
            editextMoney.setTextColor(Color.RED);
            editextMoney.setText(amount.substring(1, amount.length()));
        }
        else {
            editextMoney.setTextColor(Color.GREEN);
            editextMoney.setHintTextColor(Color.GREEN);
        }
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    formatter.setLenient(false);
                    formatter.parse(editextDate.getText().toString());
                } catch (ParseException e) {
                    Toast.makeText(getBaseContext(), "Hãy nhập ngày với định dạng Ngày/Tháng/Năm", Toast.LENGTH_SHORT).show();
                    return;
                }
                childModel.Amount = Float.valueOf(editextMoney.getText().toString());
                if(childModel.Type.equals("Chi"))
                    childModel.Amount = -childModel.Amount;
                childModel.Date = editextDate.getText().toString();
                checkAccountExist(childModel);
            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void mapping() {
        btnClose = findViewById(R.id.button_close);
        btnSave = findViewById(R.id.button_save);
        editextMoney = findViewById(R.id.editext_money);
        editextDate = findViewById(R.id.editext_date);
        editextSource = findViewById(R.id.editext_source_money);
        editextLimit = findViewById(R.id.edittext_1);
        editextTypeName = findViewById(R.id.txt_choose_spending);
        TextInputLayout = findViewById(R.id.money_layout);
    }

    private void checkAccountExist(TransactionDynamicFragmentDateItemsModel childModel){
        Log.d(thisTag, "Tiến hành kiểm tra thông tin trên firebase");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersRef = db.collection("Transactions");
        Query query = usersRef.whereEqualTo("Email", childModel.Email);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Log.d(thisTag, "Thành công lấy dữ liệu từ filestore", task.getException());
                    for (DocumentSnapshot documentsnap : task.getResult()) {
                        Log.i("YESSS FOUND!", "!!!" + documentsnap.getId() + " " + childModel.ID);
                        if(!documentsnap.getId().equals(childModel.ID))
                            continue;
                        String email = documentsnap.getString("Email");
                        String type = documentsnap.getString("Type");
                        String date = documentsnap.getString("Date");
                        Float amount = Float.valueOf(documentsnap.getString("Quantity"));
                        String typeName = documentsnap.getString("TypeName");
                        db.collection("Transactions").document(documentsnap.getId()).update("Quantity", String.valueOf(childModel.Amount));
                        db.collection("Transactions").document(documentsnap.getId()).update("Date", childModel.Date);
                        break;
                    }
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            }
        });
    }
}