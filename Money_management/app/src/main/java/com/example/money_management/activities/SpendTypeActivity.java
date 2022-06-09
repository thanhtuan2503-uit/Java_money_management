package com.example.money_management.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.money_management.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SpendTypeActivity extends AppCompatActivity {

    private ImageView btnBack;
    private CardView btnAdd;
    private RecyclerView recyclerView;
    private spendTypeModel mitem;          // Data Model
    private ArrayList<spendTypeModel> listitem;    // List dữ liệu để đưa vào recyclerview.
    private SpendTypeAdapter mitemAdapter;  // Adaptor
    private String thisTag = "SpendTypeActivity";
    private SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spend_type);
        mapping();
        Log.i("Tracking Activity Created", "SpendTypeActivity");

        // RecyclerView
        recyclerView = findViewById(R.id.spend_type_recycler_view);
        listitem = new ArrayList<>();
        getSpendingTypeData();  // Lấy dữ liệu về từ firestore.
        mitemAdapter = new SpendTypeAdapter(listitem,this);
        recyclerView.setAdapter(mitemAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        // Nút thêm loại chi tiêu
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddTypeActivity.class));
            }
        });

        // Nút quay lại
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getSpendingTypeData() {
        Log.d(thisTag, "Tiến hành kiểm tra thông tin trên firebase");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("SpendingType")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String typeName = document.getString("TypeName");
                                String icon = document.getString("Icon");
                                listitem.add(new spendTypeModel(typeName, icon));
                                mitemAdapter.notifyDataSetChanged();
                                Log.i(thisTag, typeName + "   " + icon);
                            }
                            for(int i = 0; i<listitem.size(); i++)
                                Log.w("RecyclerView Item: ", String.valueOf(listitem.get(i)));
                        } else {
                            Log.w(thisTag, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    //ánh xạ
    private void mapping(){
        btnBack = findViewById(R.id.button_back);
        btnAdd = findViewById(R.id.button_add);
    }
}