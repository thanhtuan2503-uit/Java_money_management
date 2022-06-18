package com.example.money_management.activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.money_management.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class TransactionAddActivity extends AppCompatActivity {
    private ImageView btnClose, cal;
    private TextInputEditText editextMoney;
    private TextInputEditText editTextDate;
    private TextInputEditText edittextNote;
    private TextView btnSave;
    private MaterialTextView btnChooseSpending;
    private SharedPreferences sharedpreferences; // Lấy dữ liệu đã chọn
    private boolean Activity_First_Show = false;
    private TextInputEditText dateTxt;
    private int mDate, mMonth, mYear;

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

                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    formatter.setLenient(false);
                    formatter.parse(editTextDate.getText().toString());
                } catch (ParseException e) {
                    Toast.makeText(getBaseContext(), "Hãy nhập ngày với định dạng Ngày/Tháng/Năm", Toast.LENGTH_SHORT).show();
                    return;
                }

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                // Model
                sharedpreferences = getSharedPreferences("Selected Transaction Type", Context.MODE_PRIVATE);
                String typeName =  sharedpreferences.getString("Selected Transaction Type", "");
                sharedpreferences = getSharedPreferences("Selected Transaction Pre-Type", Context.MODE_PRIVATE);
                String type = sharedpreferences.getString("Selected Transaction Pre-Type", "");
                sharedpreferences = getSharedPreferences("LoginPreferences", MODE_PRIVATE);
                String email = sharedpreferences.getString("Email", "");
                if(type.equals("Red"))
                    type = "Chi";
                if(type.equals("Green"))
                    type = "Thu";
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
                transaction.put("Email", email);
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
        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar Cal = Calendar.getInstance();
                mDate = Cal.get(Calendar.DATE);
                mMonth = Cal.get(Calendar.MONTH);
                mYear = Cal.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(TransactionAddActivity.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                        dateTxt.setText(date+"/"+month+"/"+year);
                    }
                },mYear, mMonth, mDate);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis()-1000);
                datePickerDialog.show();
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
        Log.i("Selected", txtType2SetColor);
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
        cal = findViewById(R.id.button_choose_time);
        dateTxt = findViewById(R.id.editext_date);
    }
}