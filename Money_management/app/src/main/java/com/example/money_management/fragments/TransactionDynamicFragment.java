package com.example.money_management.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.money_management.R;
import com.example.money_management.activities.MainActivity;
import com.example.money_management.activities.String2Currency;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ListIterator;

public class TransactionDynamicFragment extends Fragment {
    private View mView;
    private TransactionDynamicFragment This;
    private RecyclerView parentRecyclerView;
    private ArrayList<TransactionDynamicFragmentDatesModel> parentList;
    private ArrayList<TransactionModel> transactionList = new ArrayList<>();
    private TransactionDynamicFragmentAdapterDates parentAdapter;
    private String thisTag = "TransactionDynamicFragment";
    private Boolean FirstShow = false;
    public TextView txtIncome;
    public TextView txtOutcome;
    public TextView txtSummary;
    public AppCompatButton btnReport;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mView= inflater.inflate(R.layout.fragment_dynamic_transaction, container, false);
        This = this;
        mapping();
        FirstShow = true;
        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)mView.getContext()).ClickReport();
            }
        });
        return mView;
    }

    public void onViewCreated(View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        Integer month = getArguments().getInt("Month", 1);
        Integer year = getArguments().getInt("Year", 2022);
        Log.i("Month + Year", " " + month + "  " + year);
        parentRecyclerView = mView.findViewById(R.id.rv_transaction_fragment_parent);
        parentList = new ArrayList<>();
        transactionList = new ArrayList<>();
        getTransactionData(month, year);

        parentAdapter = new TransactionDynamicFragmentAdapterDates(parentList, v.getContext());
        parentRecyclerView.setAdapter(parentAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(v.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        parentRecyclerView.setLayoutManager(linearLayoutManager);
    }

    private void addTransactionData2List() {
        if(transactionList.size() == 0)
            return;
        Collections.sort(transactionList, (a, b) -> (Integer.valueOf(a.Date.split("/")[0]) < Integer.valueOf(b.Date.split("/")[0])) ? 1 : -1);
        String i = transactionList.get(0).Date;
        Integer income = 0;
        Integer outcome= 0;
        Integer sumAmount = 0;
        TransactionDynamicFragmentDatesModel parentModel = new TransactionDynamicFragmentDatesModel();
        parentModel.childList = new ArrayList<>();
        ListIterator<TransactionModel> iterate = transactionList.listIterator();
        while(iterate.hasNext()) {
            TransactionModel transaction = iterate.next();
            Integer amount = transaction.Amount;
            String fullDay = transaction.Date;
            String typeName = transaction.TypeName;
            String type = transaction.Type;
            String note = transaction.Note;
            String id = transaction.ID;
            if(type.equals("Chi"))
                amount = -amount;
            Log.i("Thêm vào recyclerView: ", fullDay + " " + typeName + " " + type + " " + note + " " + String.valueOf(amount) + transaction.Source);
            if(!i.equals(fullDay)){
                parentModel.Date = i;
                parentModel.Amount = sumAmount;
                parentList.add(parentModel);
                i = fullDay;
                parentModel = new TransactionDynamicFragmentDatesModel();
                parentModel.childList = new ArrayList<>();
                sumAmount = 0;
            }
            Log.i("Thêm con", "Child Child");
            sumAmount += amount;
            parentModel.childList.add(new TransactionDynamicFragmentDateItemsModel(amount, typeName, type, note, fullDay, transaction.Email, type, transaction.Source, id));
            if(!iterate.hasNext()) {
                parentModel.Date = fullDay;
                parentModel.Amount = sumAmount;
                parentList.add(parentModel);
            }
            if(type.equals("Thu"))
                income+=amount;
            else
                outcome+=amount;
        }
        iterate = transactionList.listIterator();
        while(iterate.hasNext()) {
            TransactionModel transaction = iterate.next();
            Integer amount = transaction.Amount;
            String fullDay = transaction.Date;
            String typeName = transaction.TypeName;
            String type = transaction.Type;
            String note = transaction.Note;
            Log.i("Thêm vào recyclerView: ", fullDay + " " + typeName + " " + type + " " + note + " " + String.valueOf(amount));
        }
        setIncomeOutcome(String.valueOf(income), String.valueOf(outcome), String.valueOf(income + outcome));
        parentAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume(){
        super.onResume();
        if(FirstShow == true){
            FirstShow = false;
            return;
        }
        parentList.clear();
        parentAdapter.clear();
        transactionList.clear();
        Integer month = getArguments().getInt("Month", 1);
        Integer year =  getArguments().getInt("Year", 2022);
        getTransactionData(month, year);
        Log.i("Month + Year", " " + month + "  " + year);
        parentAdapter.notifyDataSetChanged();
    }


    public void getTransactionData(int MonthFilter, int YearFilter) {
        SharedPreferences sharedpreferences = getActivity().getApplicationContext().getSharedPreferences("LoginPreferences", Context.MODE_PRIVATE);
        String logged_Email = sharedpreferences.getString("Email", "");
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
                                String[] dateSplit = date.split("/");
                                if(date == null)
                                    continue;
                                try {
                                    if(Integer.valueOf(dateSplit[1]) != MonthFilter)
                                        continue;
                                    if(Integer.valueOf(dateSplit[2]) != YearFilter)
                                        continue;
                                }catch(ArrayIndexOutOfBoundsException e) {
                                    continue;
                                }

                                String email = document.getString("Email");
                                String type = document.getString("Type");
                                String note = document.getString("Note");
                                Integer amount = 0;
                                try {
                                    amount = Integer.valueOf(document.getString("Quantity"));
                                }catch (NumberFormatException e){
                                    amount = 0;
                                }
                                String source = document.getString("Source");
                                String typeName = document.getString("TypeName");
                                String id = document.getId();

                                if(email == null) continue;
                                if(!email.equals(logged_Email))
                                    continue;
                                Log.i("Dữ liệu từ firestore", email);
                                // Filter
                                transactionList.add(new TransactionModel(email, date, note, amount, source, type, typeName, id));
                            }
                        }
                        addTransactionData2List();
                    }
                });
    }

    private void setIncomeOutcome(String income, String outcome, String summary){
        String2Currency convert = new String2Currency();
        if(Float.valueOf(summary) >= 0)
            txtSummary.setTextColor(Color.rgb(0, 178, 18));
        else
            txtSummary.setTextColor(Color.rgb(246, 0,0 ));
        income = convert.convertString2Currency(income);
        outcome = convert.convertString2Currency(outcome);
        summary = convert.convertString2Currency(summary);
        txtIncome.setText(income);
        txtOutcome.setText(outcome);
        txtSummary.setText(summary);
        sumSourceAmount();
    }

    private void sumSourceAmount(){
        SharedPreferences sharedpreferences = getActivity().getApplicationContext().getSharedPreferences("LoginPreferences", Context.MODE_PRIVATE);
        String logged_Email = sharedpreferences.getString("Email", null);
        Log.d(thisTag, "Tiến hành kiểm tra thông tin trên firebase");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Transactions")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Integer income = 0;
                        Integer outcome = 0;
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String email = document.getString("Email");
                                String type = document.getString("Type");
                                Integer amount = 0;
                                try {
                                    amount = Integer.valueOf(document.getString("Quantity"));
                                }catch (NumberFormatException e){
                                    amount = 0;
                                }

                                if(email == null) continue;
                                if(!email.equals(logged_Email))
                                    continue;
                                if(type.equals("Thu"))
                                    income += amount;
                                else
                                    outcome += amount;
                                Log.i("Dữ liệu từ firestore", email);
                            }
                            push2FireStoreSumAmount(income, outcome);
                        }
                    }

                });
    }

    private void push2FireStoreSumAmount(Integer income, Integer outcome){
        SharedPreferences sharedpreferences = mView.getContext().getSharedPreferences("LoginPreferences", Context.MODE_PRIVATE); // Chọn file có tên "LoginPreferences"
        String logged_Email = sharedpreferences.getString("Email", "");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersRef = db.collection("MoneySource");
        Query query = usersRef.whereEqualTo("Email", logged_Email);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot documentsnap : task.getResult()) {
                        String email = documentsnap.getString("Email");
                        if(!email.equals(logged_Email))
                            continue;
                        db.collection("MoneySource").document(documentsnap.getId()).update("Amount", String.valueOf(income - outcome));
                        break;
                    }
                }else{
                    Log.d(thisTag, "Không thành công lấy dữ liệu từ filestore", task.getException());
                }
                TransactionFragment parentFrag = ((TransactionFragment) This.getParentFragment());
                parentFrag.updateMoneyAmount();
            }
        });
    }

    private void mapping() {
        //btnChoose = mView.findViewById(R.id.btn_choose);
        txtIncome = mView.findViewById(R.id.tvIncome);
        txtOutcome = mView.findViewById(R.id.tvOutcome);
        txtSummary = mView.findViewById(R.id.tvSummary);
        btnReport = mView.findViewById(R.id.button_report);
    }
}