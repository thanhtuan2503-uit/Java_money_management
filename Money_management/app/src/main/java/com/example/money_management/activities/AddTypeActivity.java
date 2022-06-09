package com.example.money_management.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.money_management.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddTypeActivity extends AppCompatActivity {
    private MaterialTextView btnImage;
    private ImageView btnBack;
    private TextView btnSave;
    private TextInputEditText editextTypeName;
    private TextInputEditText editextLimitTransaction;
    private TextInputEditText editextDescribe;
    private String thisTag = "AddTypeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_type);
        mapping();
        Log.i("Tracking Activity Created", "AddTypeActivity");
        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Bấm save
        btnSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View view){
                String typeName = editextTypeName.getText().toString();
                String describe = editextDescribe.getText().toString();
                Float limit = Float.valueOf(editextLimitTransaction.getText().toString());
                addSpendingType("", typeName, 100.0F, describe);
            }
        });
    }

    // Đưa dữ liệu lên firestore.
    private void addSpendingType(String icon, String typeName, Float limit, String describe){
        Log.i("Tracking Activity Action", "addSpendingType");
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Model
        Map<String, Object> spendingType = new HashMap<>();
        spendingType.put("Icon", icon);
        spendingType.put("TypeName", typeName);
        spendingType.put("Limit", limit);
        spendingType.put("Describe", describe);

        Log.i("Tracking Activity Action", "Done create model");
        // Thêm vào firestore
        db.collection("SpendingType")
                .add(spendingType)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.i(thisTag, "DocumentSnapshot added with ID: " + documentReference.getId());
                        Log.i("Tracking Activity Action", "Done addSpendingType");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i(thisTag, "Error adding document", e);
                        Log.i("Tracking Activity Action", "Failed addSpendingType");
                    }
                });
    }

    //ánh xạ
    private void mapping() {
        btnImage = findViewById(R.id.txt_choose_image);
        btnBack = findViewById(R.id.button_back);
        btnSave = findViewById(R.id.button_save);
        editextTypeName = findViewById(R.id.editext_typename);
        editextLimitTransaction = findViewById(R.id.editext_limit_transaction);
        editextDescribe = findViewById(R.id.editext_describe);
    }
}