package com.example.money_management.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;


import com.example.money_management.R;
import com.example.money_management.fragments.AccountFragment;
import com.example.money_management.fragments.NoteFragment;
import com.example.money_management.fragments.ReportFragment;
import com.example.money_management.fragments.TransactionFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    public static final String DBPullTAG = "ReadFireBaseDataTAG";
    public static final String DBPushTAG = "WriteFireBaseDataTAG";
    BottomNavigationView bottomNavigationView;
    AccountFragment accountFragment = new AccountFragment();
    NoteFragment noteFragment= new NoteFragment();
    TransactionFragment transactionFragment = new TransactionFragment();
    ReportFragment reportFragment = new ReportFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseTesting();

        // Thanh Điều Hướng
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,transactionFragment).commit();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.transaction:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,transactionFragment).commit();
                        return true;
                    case R.id.account:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,accountFragment).commit();
                        return true;
                    case R.id.note:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,noteFragment).commit();
                        return true;
                    case R.id.report:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,reportFragment).commit();
                        return true;
                }
                return false;
            }
        });
    }


    // Ham test doc, ghi du lieu tu firestore
    public void databaseTesting(){
        // Tao instance
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Day du lieu len firestore
        Map<String, Object> account = new HashMap<>();
        account.put("Email", "19520265@gm.uit.edu.vn");
        account.put("Username", "Thach09012001");
        account.put("ID", "Thach1");
        account.put("Password", "Thach0901");
        account.put("Name", "Thach");

        // Add a new document with a generated ID
        db.collection("Accounts")
                .add(account)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(DBPushTAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(DBPushTAG, "Error adding document", e);
                    }
                });

        // Lay du lieu tu firestore
        db.collection("Accounts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(DBPullTAG, "Tag Data" + document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w(DBPullTAG, "Error getting documents.", task.getException());
                        }
                    }
                });


    }

}