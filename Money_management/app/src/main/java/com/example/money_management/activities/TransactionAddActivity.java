package com.example.money_management.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class TransactionAddActivity extends AppCompatActivity {
    private ImageView btnClose;
    private TextInputEditText editextMoney;
    private TextInputEditText editTextDate;
    private TextInputEditText edittextNote;
    private TextView btnSave;
    private MaterialTextView btnChooseSpending;
    private SharedPreferences sharedpreferences; // Lấy dữ liệu đã chọn
    private boolean Activity_First_Show = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_add);
        mapping();
        Log.i("Tracking Activity Created", "TransactionAddActivity");
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Lưu dữ liệu
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                // Model
                sharedpreferences = getSharedPreferences("Selected Transaction Type", Context.MODE_PRIVATE);
                String typeName =  sharedpreferences.getString("Selected Transaction Type", "");
                sharedpreferences = getSharedPreferences("Selected Transaction Pre-Type", Context.MODE_PRIVATE);
                String type = sharedpreferences.getString("Selected Transaction Pre-Type", "");
                if(type.equals("Red"))
                    type = "Chi";
                if(type.equals("Green"))
                    type = "Green";
                String quantity = editextMoney.getText().toString();
                String note = edittextNote.getText().toString();
                String date = editTextDate.getText().toString();

                Map<String, Object> transaction = new HashMap<>();
                transaction.put("Type", type);
                transaction.put("TypeName", typeName);
                transaction.put("Source", "Tiền Mặt");
                transaction.put("Quantity", quantity);
                transaction.put("Note", note);
                transaction.put("Date", date);
                db.collection("Transactions")
                        .add(transaction)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(view.getContext(),
                                                "Thêm thành công", Toast.LENGTH_SHORT)
                                        .show();
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
            }
        });

        btnChooseSpending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ChooseSpendingActivity.class));
            }
        });


    }

    @Override
    public void onResume(){
        super.onResume();
        if(!Activity_First_Show){
            Activity_First_Show = true;
            return;
        }

        sharedpreferences = getSharedPreferences("Selected Transaction Type", Context.MODE_PRIVATE);
        btnChooseSpending.setText(sharedpreferences.getString("Selected Transaction Type", ""));
        sharedpreferences = getSharedPreferences("Selected Transaction Pre-Type", Context.MODE_PRIVATE);
        String txtType2SetColor = sharedpreferences.getString("Selected Transaction Pre-Type", "");
        if(txtType2SetColor.equals("Red")) {
            Log.d("Tracking Activity Action", txtType2SetColor);
            editextMoney.setTextColor(Color.RED);
        }
        else {
            Log.d("Tracking Activity Action", txtType2SetColor);
            editextMoney.setTextColor(Color.GREEN);
        }
    }

    //Ánh xạ
    private void mapping(){
        btnClose = findViewById(R.id.button_close);
        btnSave = findViewById(R.id.button_save);
        btnChooseSpending = findViewById(R.id.txt_choose_spending);
        editextMoney = findViewById(R.id.editext_money);
        editTextDate = findViewById(R.id.editext_date);
        edittextNote = findViewById(R.id.edittext_note);
    }
}