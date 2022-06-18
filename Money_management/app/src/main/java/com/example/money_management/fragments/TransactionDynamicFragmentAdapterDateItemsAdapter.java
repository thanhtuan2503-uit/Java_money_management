package com.example.money_management.fragments;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.money_management.R;
import com.example.money_management.activities.DetailTransactionActivity;
import com.example.money_management.activities.MainActivity;
import com.example.money_management.activities.SelectedTransactionActivity;
import com.example.money_management.activities.SpendTypeActivity;
import com.example.money_management.activities.String2Currency;

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
        View itemView = inflater.inflate(R.layout.fragment_transaction_date_items,parent,false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String2Currency convert = new String2Currency();
        TransactionDynamicFragmentDateItemsModel childItem = childList.get(position);
        holder.txtTransaction.setText(childItem.TypeName);
        holder.txtAmount.setText(convert.convertString2Currency(String.valueOf(childItem.Amount)));
        holder.txtAmountHD.setText(String.valueOf(childItem.Amount));
        holder.txtTypeHidden.setText(String.valueOf(childItem.Type));
        holder.txtEmailHidden.setText(String.valueOf(childItem.Email));
        holder.txtDateHidden.setText(String.valueOf(childItem.Date));
        holder.txtTransactionID.setText(String.valueOf(childItem.ID));
        holder.txtNoteHidden.setText(String.valueOf(childItem.Note));

        if(childItem.Amount > 0)
            holder.txtAmount.setTextColor(Color.rgb(0, 178, 18));
        if(childItem.Amount < 0)
            holder.txtAmount.setTextColor(Color.rgb(246, 0, 0));
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
        public TextView txtAmountHD;
        public TextView txtEmailHidden;
        public TextView txtTypeHidden;
        public TextView txtDateHidden;
        public TextView txtNoteHidden;
        public TextView txtTransactionID;
        public LinearLayout btnItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgView = itemView.findViewById(R.id.transaction_pic);
            txtTransaction = itemView.findViewById(R.id.transaction_typeName);
            txtAmount = itemView.findViewById(R.id.transaction_amount);
            txtAmountHD = itemView.findViewById(R.id.transaction_amountHD);
            txtEmailHidden = itemView.findViewById(R.id.transaction_Email_HD);
            txtDateHidden = itemView.findViewById(R.id.transaction_Date_HD);
            txtTypeHidden = itemView.findViewById(R.id.transaction_Type_HD);
            txtTransactionID = itemView.findViewById(R.id.transaction_ID_HD);
            txtNoteHidden = itemView.findViewById(R.id.transaction_Note_HD);
            btnItem = itemView.findViewById(R.id.btn_movie);

            //Xử lý khi nút Chi tiết được bấm
            btnItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try{
                        String2Currency cv = new String2Currency();
                        sharedpreferences = view.getContext().getSharedPreferences("TransactionDetails", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit(); // Lưu trạng thái đăng nhập
                        editor.putString("Type", txtTypeHidden.getText().toString());
                        editor.putString("TypeName", txtTransaction.getText().toString());
                        editor.putString("Email", txtEmailHidden.getText().toString());
                        editor.putString("Date", txtDateHidden.getText().toString());
                        editor.putString("Amount", txtAmount.getText().toString());
                        editor.putString("AmountHD", (txtAmountHD.getText().toString()));
                        editor.putString("ID", txtTransactionID.getText().toString());
                        editor.putString("Note", txtNoteHidden.getText().toString());
                        editor.commit();
                        view.getContext().startActivity(new Intent(view.getContext().getApplicationContext(), SelectedTransactionActivity.class));
                    }
                    catch (NullPointerException e){
                        return;
                    }
                }
            });
        }
    }
    private void getTransactionDescribe(String email, String ID){

    }
}










