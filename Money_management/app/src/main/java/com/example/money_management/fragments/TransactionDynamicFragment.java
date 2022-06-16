package com.example.money_management.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.money_management.R;
import com.example.money_management.activities.MainActivity;
import com.example.money_management.activities.SpendTypeAdapter;
import com.example.money_management.activities.String2Currency;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import org.w3c.dom.Text;

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
    public TextView txtIncome;
    public TextView txtOutcome;
    public TextView txtSummary;
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
        parentRecyclerView = mView.findViewById(R.id.rv_transaction_fragment_parent);
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
            Log.i("ADD child", "Child Child");
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
        getTransactionData(6);
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
                                String[] dateSplit = date.split("/");
                                if(date == null)
                                    continue;
                                try {
                                    if(Integer.valueOf(dateSplit[1]) != MonthFilter)
                                        continue;
                                }catch(ArrayIndexOutOfBoundsException e){
                                     continue;
                                }

                                String email = document.getString("Email");
                                String type = document.getString("Type");
                                String note = document.getString("Note");
                                Integer amount = Integer.valueOf(document.getString("Quantity"));
                                String source = document.getString("Source");
                                String typeName = document.getString("TypeName");
                                String id = document.getId();
                                // Lấy dữ liệu của tài khoản này.
//                                if(!email.equals(logged_Email))
//                                    continue;
                                if(email == null) continue;
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
            txtSummary.setTextColor(Color.GREEN);
        else
            txtSummary.setTextColor(Color.RED);
        income = convert.convertString2Currency(income);
        outcome = convert.convertString2Currency(outcome);
        summary = convert.convertString2Currency(summary);
        txtIncome.setText(income);
        txtOutcome.setText(outcome);
        txtSummary.setText(summary);
    }

    private void mapping() {
        //btnChoose = mView.findViewById(R.id.btn_choose);
        txtIncome = mView.findViewById(R.id.tvIncome);
        txtOutcome = mView.findViewById(R.id.tvOutcome);
        txtSummary = mView.findViewById(R.id.tvSummary);
    }
}