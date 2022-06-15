package com.example.money_management.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.money_management.R;
import com.example.money_management.activities.MainActivity;
import com.example.money_management.activities.SpendTypeAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.ListIterator;

public class TransactionDynamicFragment extends Fragment {
    private View mView;
    private RecyclerView parentRecyclerView;
    private ArrayList<TransactionDynamicFragmentDatesModel> parentList;
    private ArrayList<TransactionModel> transactionList = new ArrayList<>();
    private TransactionDynamicFragmentAdapterDates parentAdapter;
    private String thisTag = "TransactionDynamicFragment";
    private Boolean FirstShow = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mView= inflater.inflate(R.layout.fragment_dynamic_transaction, container, false);
        mapping();
        FirstShow = true;


//        btnChoose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getContext(), SelectedTransactionActivity.class));
//
//            }
//        });
        return mView;
    }

    public void onViewCreated(View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        parentRecyclerView = mView.findViewById(R.id.transaction_dates_recycler_view);
        parentList = new ArrayList<>();
        transactionList = new ArrayList<>();
        getTransactionData(6);

        parentAdapter = new TransactionDynamicFragmentAdapterDates(parentList, v.getContext());
        parentRecyclerView.setAdapter(parentAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(v.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        parentRecyclerView.setLayoutManager(linearLayoutManager);
    }


    private void addTransactionData2List() {
        Collections.sort(transactionList, (a, b) -> (Integer.valueOf(a.Date.split("/")[0]) < Integer.valueOf(b.Date.split("/")[0])) ? 1 : -1);

        String i = transactionList.get(0).Date;
        Float sumAmount = 0f;
        TransactionDynamicFragmentDatesModel parentModel = new TransactionDynamicFragmentDatesModel();
        parentModel.childList = new ArrayList<>();
        ListIterator<TransactionModel> iterate = transactionList.listIterator();
        while(iterate.hasNext()) {
            TransactionModel transaction = iterate.next();
            Float amount = transaction.Amount;
            String fullDay = transaction.Date;
            String typeName = transaction.TypeName;
            String type = transaction.Type;
            String note = transaction.Note;
            if(type.equals("Chi"))
                amount = -amount;
            Log.i("Thêm vào recyclerView: ", fullDay + " " + typeName + " " + type + " " + note + " " + String.valueOf(amount));
            if(!i.equals(fullDay)){
                parentModel.Date = i;
                parentModel.Amount = sumAmount;
                parentList.add(parentModel);
                i = fullDay;
                parentModel = new TransactionDynamicFragmentDatesModel();
                parentModel.childList = new ArrayList<>();
                sumAmount = 0f;
            }
            Log.i("ADD child", "Child Child");
            sumAmount += amount;
            parentModel.childList.add(new TransactionDynamicFragmentDateItemsModel(amount, typeName, type, note));
            if(!iterate.hasNext()) {
                parentModel.Date = fullDay;
                parentModel.Amount = sumAmount;
                parentList.add(parentModel);
            }
        }
        iterate = transactionList.listIterator();
        while(iterate.hasNext()) {
            TransactionModel transaction = iterate.next();
            Float amount = transaction.Amount;
            String fullDay = transaction.Date;
            String typeName = transaction.TypeName;
            String type = transaction.Type;
            String note = transaction.Note;
            Log.i("Thêm vào recyclerView: ", fullDay + " " + typeName + " " + type + " " + note + " " + String.valueOf(amount));
        }
        parentAdapter.notifyDataSetChanged();
    }

    private void getTransactionData(int MonthFilter) {
        SharedPreferences sharedpreferences = this.getActivity().getSharedPreferences("LoginPreferences", Context.MODE_PRIVATE);
        String logged_Email = sharedpreferences.getString("Email", null);

        Log.d(thisTag, "Tiến hành kiểm tra thông tin trên firebase");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Transactions")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        // Lấy dữ liệu các giao dịch từ firestore
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String date = document.getString("Date");
                                Log.i("Dữ liệu từ firestore", date);
                                if(date == null)
                                    continue;
                                String[] dateSplit = date.split("/");
                                if(Integer.valueOf(dateSplit[1]) != MonthFilter)
                                    continue;
                                String email = document.getString("Email");
                                String type = document.getString("Type");
                                String note = document.getString("Note");
                                Float amount = Float.valueOf(document.getString("Quantity"));
                                String source = document.getString("Source");
                                String typeName = document.getString("TypeName");
                                // Lấy dữ liệu của tài khoản này.
//                                if(!email.equals(logged_Email))
//                                    continue;
                                Log.i("Dữ liệu từ firestore", email);
                                // Filter
                                transactionList.add(new TransactionModel(email, date, note, amount, source, type, typeName));
                            }

                        }
                        addTransactionData2List();

                    }
                });
    }

    private void mapping() {
        //btnChoose = mView.findViewById(R.id.btn_choose);
    }
}