package com.example.money_management.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.money_management.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class ChangeNameActivity extends AppCompatActivity {
    private ImageView btnBack;
    private AppCompatButton btnChangeName;
    private TextInputEditText txtNewName;
    private SharedPreferences sharedpreferences; // Lấy dữ liệu đăng nhập.
    private final String thisTag = "ChangeNameActivityTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_name);
        mapping();
        Log.i("Tracking Activity Created", "ChangeNameActivity");
        sharedpreferences = getSharedPreferences("LoginPreferences", Context.MODE_PRIVATE); // Lay thong tin dang nhap
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChangeNameActivity.this,AccountInformationActivity.class));
                finish();
            }
        });

        btnChangeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enteredData = txtNewName.getText().toString();
                String email = sharedpreferences.getString("Email", null);
                if (enteredData.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please Enter the Data", Toast.LENGTH_SHORT).show();
                } else {
                    changeName(email, enteredData);
                    SystemClock.sleep(1000);
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));  // Mở trang chủ
                    finish();
                }
            }
        });
    }

    // Kiểm tra trên firebase nếu tài khoản có tồn tại và cập nhật tên
    private void changeName(String email, String newName){
        // Cập nhật tên lên firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersRef = db.collection("Accounts");
        Query query = usersRef.whereEqualTo("Email", email);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Log.d(thisTag, "Thành công lấy dữ liệu từ filestore", task.getException());
                    for (DocumentSnapshot documentsnap : task.getResult()) {
                        String account = documentsnap.getString("Email");
                        if (account.equals(email)) {
                            db.collection("Accounts").document(documentsnap.getId()).update("Username", newName);
                            Log.d(thisTag, "Thành công đổi tên" + "    "  + documentsnap.getId(), task.getException());
                            return;
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),AccountInformationActivity.class));
        finish();
    }

    private void mapping(){
        btnBack = findViewById(R.id.button_back);
        btnChangeName = findViewById(R.id.button_change_name);
        txtNewName = findViewById(R.id.txt_new_name);
    }
}