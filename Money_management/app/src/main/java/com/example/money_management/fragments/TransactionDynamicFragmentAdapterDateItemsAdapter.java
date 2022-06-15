package com.example.money_management.fragments;
import android.content.Context;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class TransactionDynamicFragmentAdapterDateItemsAdapter extends RecyclerView.Adapter<TransactionDynamicFragmentAdapterDateItemsAdapter.ViewHolder> {

    private List<TransactionDynamicFragmentDateItemsModel> childList;
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
        public CardView btnItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgView = itemView.findViewById(R.id.iv_transaction_pic1);
            txtTransaction = itemView.findViewById(R.id.tv_transaction_type_name);
            txtAmount = itemView.findViewById(R.id.tv_price);
            btnItem = itemView.findViewById(R.id.button_movie);

            //Xử lý khi nút Chi tiết được bấm
            btnItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(),
                                    txtTransaction.getText() +" | "
                                            + " Demo function", Toast.LENGTH_SHORT)
                            .show();
                }
            });
        }
    }
}










