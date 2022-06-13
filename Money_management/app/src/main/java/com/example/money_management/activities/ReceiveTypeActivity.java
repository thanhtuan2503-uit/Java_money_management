package com.example.money_management.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.money_management.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ReceiveTypeActivity extends AppCompatActivity {
    private ImageView btnBack;
    private CardView btnAdd, btnSalary, btnHotHui;
    private RecyclerView recyclerView;
    private spendTypeModel mitem;          // Data Model
    private ArrayList<ReceiveTypeModel> listitem;    // List dữ liệu để đưa vào recyclerview.
    private ReceiveTypeAdapter mitemAdapter;  // Adaptor
    private String thisTag = "ReceiveTypeActivity";
    private SharedPreferences sharedpreferences;
    private Boolean FirstShow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_type);
        mapping();
        FirstShow = true;
        Log.i("Tracking Activity Created", "ReceiveTypeActivity");

        // RecyclerView
        recyclerView = findViewById(R.id.spend_type_recycler_view);
        listitem = new ArrayList<>();
        getReceiveTypeData();
        mitemAdapter = new ReceiveTypeAdapter(listitem,this);
        recyclerView.setAdapter(mitemAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddTypeActivity.class));
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        if(FirstShow == true){
            FirstShow = false;
            return;
        }
        listitem.clear();
        mitemAdapter.clear();
        getReceiveTypeData();
        mitemAdapter.notifyDataSetChanged();
    }

    private void getReceiveTypeData() {
        Log.d(thisTag, "Tiến hành kiểm tra thông tin trên firebase");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("IncomeType")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String typeName = document.getString("TypeName");
                                String icon = document.getString("Icon");
                                listitem.add(new ReceiveTypeModel(typeName, icon));
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