package com.example.money_management.fragments;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.money_management.R;
import com.example.money_management.activities.DetailTransactionActivity;
import com.example.money_management.activities.MainActivity;
import com.example.money_management.activities.SpendTypeActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class TransactionDynamicFragmentAdapterDateItemsAdapter extends RecyclerView.Adapter<TransactionDynamicFragmentAdapterDateItemsAdapter.ViewHolder> {

    private List<TransactionDynamicFragmentDateItemsModel> childList;
    private SharedPreferences sharedpreferences;
    private Context Context;

    public TransactionDynamicFragmentAdapterDateItemsAdapter(ArrayList<TransactionDynamicFragmentDateItemsModel> child, Context context) {
        this.childList = child;
        this.Context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(Context);
        View itemView = inflater.inflate(R.layout.fragment_dynamic_transaction_dates_items_layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TransactionDynamicFragmentDateItemsModel childItem = childList.get(position);
        holder.txtTransaction.setText(childItem.TypeName);
        holder.txtAmount.setText(String.valueOf(childItem.Amount));
        holder.txtTypeHidden.setText(String.valueOf(childItem.Type));
        holder.txtEmailHidden.setText(String.valueOf(childItem.Email));
        holder.txtDateHidden.setText(String.valueOf(childItem.Date));
        holder.txtTransactionID.setText(String.valueOf(childItem.ID));

        if(childItem.Amount > 0)
            holder.txtAmount.setTextColor(Color.GREEN);
        if(childItem.Amount < 0)
            holder.txtAmount.setTextColor(Color.RED);
    }

    @Override
    public int getItemCount() {
        if(childList != null)
            return childList.size();
        return 0;
    }

    public void clear() {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgView;
        public TextView txtTransaction;
        public TextView txtAmount;
        public TextView txtEmailHidden;
        public TextView txtTypeHidden;
        public TextView txtDateHidden;
        public TextView txtLimitHidden;
        public TextView txtTransactionID;
        public CardView btnItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgView = itemView.findViewById(R.id.iv_transaction_pic1);
            txtTransaction = itemView.findViewById(R.id.tv_transaction_type_name);
            txtAmount = itemView.findViewById(R.id.tv_price);
            txtEmailHidden = itemView.findViewById(R.id.tv_Email_Hidden);
            txtDateHidden = itemView.findViewById(R.id.tv_Date_Hidden);
            txtTypeHidden = itemView.findViewById(R.id.tv_TypeName_Hidden);
            btnItem = itemView.findViewById(R.id.button_movie);
            txtTransactionID = itemView.findViewById(R.id.tv_ID_Hidden);

            //Xử lý khi nút Chi tiết được bấm
            btnItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sharedpreferences = view.getContext().getSharedPreferences("TransactionDetails", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit(); // Lưu trạng thái đăng nhập
                    editor.putString("Type", txtTypeHidden.getText().toString());
                    editor.putString("TypeName", txtTransaction.getText().toString());
                    editor.putString("Email", txtEmailHidden.getText().toString());
                    editor.putString("Date", txtDateHidden.getText().toString());
                    editor.putString("Amount", txtAmount.getText().toString());
                    editor.putString("ID", txtTransactionID.getText().toString());
                    editor.commit();
                    view.getContext().startActivity(new Intent(view.getContext().getApplicationContext(), DetailTransactionActivity.class));
                }
            });
        }
    }
}










