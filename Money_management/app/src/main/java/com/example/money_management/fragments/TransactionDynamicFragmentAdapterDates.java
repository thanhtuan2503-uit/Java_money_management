package com.example.money_management.fragments;
import android.app.Activity;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.money_management.R;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;



public class TransactionDynamicFragmentAdapterDates extends RecyclerView.Adapter<TransactionDynamicFragmentAdapterDates.ViewHolder> {

    private Context Context;
    private ArrayList<TransactionDynamicFragmentDatesModel> parentList;

    public TransactionDynamicFragmentAdapterDates(
            ArrayList<TransactionDynamicFragmentDatesModel> parent,
            Context context) {
        this.parentList = parent;
        this.Context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(Context);
        View itemView = inflater.inflate(R.layout.fragment_dynamic_transaction_dates_layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TransactionDynamicFragmentDatesModel parentItem = parentList.get(position);
        String[] DateSplit = new String[3];
        if(parentItem.Date != null)
            DateSplit = parentItem.Date.split("/");
        holder.txtFullDate.setText(parentItem.Date);
        holder.txtAmount.setText(String.valueOf(parentItem.Amount));
        holder.txtDate.setText(DateSplit[0]);
        holder.txtDay.setText(getDayOfWeek(parentItem.Date));
        if(parentItem.Amount < 0)
            holder.txtAmount.setTextColor(Color.RED);
        else
            holder.txtAmount.setTextColor(Color.GREEN);
        TransactionDynamicFragmentAdapterDateItemsAdapter childAdapter = new TransactionDynamicFragmentAdapterDateItemsAdapter(parentItem.getChildList(), holder.itemView.getContext());
        holder.childRV.setAdapter(childAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(holder.itemView.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        holder.childRV.setLayoutManager(linearLayoutManager);
        childAdapter.notifyDataSetChanged();
    }

    public String getDayOfWeek(String date){
        if(date == null)
            return "";

        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
        Date dt1 = null;
        try {
            dt1 = format1.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat format2 = new SimpleDateFormat("EEEE");
        String finalDay = format2.format(dt1);
        return finalDay;
    }

    @Override
    public int getItemCount() {
        return parentList.size();
    }

    public void clear() {
        int size = parentList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                parentList.remove(0);
            }
            notifyItemRangeRemoved(0, size);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtFullDate;
        public TextView txtDate;
        public TextView txtDay;
        public TextView txtAmount;
        public CardView btnItem;
        public RecyclerView childRV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtFullDate = itemView.findViewById(R.id.tv_full_date);
            txtDate = itemView.findViewById(R.id.tv_transaction_date);
            txtDay = itemView.findViewById(R.id.tv_transaction_day);
            txtAmount = itemView.findViewById(R.id.tv_amount);
            childRV = itemView.findViewById((R.id.transaction_dates_items_recycler_view));
        }
    }
}










