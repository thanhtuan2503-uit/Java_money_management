package com.example.money_management.activities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Icon;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.money_management.R;

import java.util.ArrayList;
import java.util.List;

public class ReceiveTypeAdapter extends RecyclerView.Adapter<ReceiveTypeAdapter.ViewHolder> {

    private List<ReceiveTypeModel> Types;
    private Context Context;
    private SharedPreferences sharedpreferences;

    public ReceiveTypeAdapter(ArrayList<ReceiveTypeModel> items, Context context) {
        this.Types = items;
        this.Context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(Context);
        View itemView = inflater.inflate(R.layout.spend_type_layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ReceiveTypeModel type = Types.get(position);
        holder.imgView.setImageIcon(Icon.createWithContentUri(type.Icon));
        holder.txtTransaction.setText(type.TypeName);
    }

    @Override
    public int getItemCount() {
        return Types.size();
    }

    public void clear() {
        int size = Types.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                Types.remove(0);
            }

            notifyItemRangeRemoved(0, size);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgView;
        public TextView txtTransaction;
        public CardView btnItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgView = itemView.findViewById(R.id.img_movie);
            txtTransaction = itemView.findViewById(R.id.txt_add_transaction);
            btnItem = itemView.findViewById(R.id.button_movie);

            //Xử lý khi nút Chi tiết được bấm
            btnItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(),
                                    txtTransaction.getText() +" | "
                                            + " Demo function", Toast.LENGTH_SHORT)
                            .show();
                    sharedpreferences = view.getContext().getSharedPreferences("Selected Transaction Type", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("Selected Transaction Type", txtTransaction.getText().toString());
                    editor.putString("Selected Transaction Limit", txtTransaction.getText().toString());
                    editor.commit();
                    ((ReceiveTypeActivity)Context).onBackPressed();
                }
            });
        }
    }
}










