//package com.example.money_management.fragments;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.fragment.app.Fragment;
//
//import com.example.money_management.R;
//import com.github.mikephil.charting.charts.BarChart;
//import com.github.mikephil.charting.components.XAxis;
//import com.github.mikephil.charting.data.BarData;
//import com.github.mikephil.charting.data.BarDataSet;
//import com.github.mikephil.charting.data.BarEntry;
//import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.QueryDocumentSnapshot;
//import com.google.firebase.firestore.QuerySnapshot;
//
//import java.util.ArrayList;
//
//public class ReportDynamicFragment extends Fragment {
//
//    BarChart barChart, barChart1, barChart2;
//    View mView;
//    ArrayList<TransactionModel> transactionList;  // arraylist chứa các giao dịch
//    private SharedPreferences sharedpreferences;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        mView = inflater.inflate(R.layout.fragment_dynamic_report, container, false);
//        transactionList = new ArrayList<>();
//        LayDuLieuTransactions();
//
//        return mView;
//    }
//
//    private void DuaDuLieuVaoSoDo(){
//        // barchart 1: Thu nhap rong
//        Log.i("Báo cáo: ", "Thu nhập ròng");
//        barChart = mView.findViewById(R.id.chart);
//        BarDataSet barDataSet1 = new BarDataSet(barEntries1(),"Thu");
//        barDataSet1.setColor(Color.GREEN);
//        BarDataSet barDataSet2 = new BarDataSet(barEntries2(),"Chi");
//        barDataSet2.setColor(Color.RED);
//
//        BarData data = new BarData(barDataSet1, barDataSet2);
//        barChart.setData(data);
//        String[] date = new String[]{"1/2 - 7/2", "8/2 - 14/2", "15/2 - 21/2", "22/2 - 28/2", "29/2 - 31/2"};
//        XAxis xAxis = barChart.getXAxis();
//        xAxis.setValueFormatter(new IndexAxisValueFormatter(date));
//        xAxis.setCenterAxisLabels(true);
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setGranularity(1);
//        xAxis.setGranularityEnabled(true);
//
//        barChart.setDragEnabled(true);
//        barChart.setVisibleXRangeMaximum(2);
//
//        float barSpace = 0.05f;
//        float groupSpace = 0.2f;
//        data.setBarWidth(0.35f);
//
//        barChart.getXAxis().setAxisMinimum(0);
//        barChart.getXAxis().setAxisMaximum(0+barChart.getBarData().getGroupWidth(groupSpace, barSpace)*5);
//        barChart.getAxisLeft().setAxisMinimum(0);
//
//        barChart.groupBars(0, groupSpace, barSpace);
//
//        barChart.invalidate();
//        // barchart 2: Tong thu
//        Log.i("Báo cáo: ", "Tổng thu");
//        barChart1 = mView.findViewById(R.id.chart1);
//        BarDataSet barDataSet3 = new BarDataSet(barEntries3(), "Thu");
//        barDataSet3.setColor(Color.GREEN);
//
//        BarData data1 = new BarData();
//        data1.addDataSet(barDataSet3);
//
//        String[] content = new String[]{"Hốt hụi", "A trả nợ", "Lương"};
//        xAxis = barChart1.getXAxis();
//        xAxis.setValueFormatter(new IndexAxisValueFormatter(content));
//        xAxis.setCenterAxisLabels(true);
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setGranularity(1);
//        xAxis.setGranularityEnabled(true);
//
//        barChart1.setDragEnabled(true);
//        barChart1.setVisibleXRangeMaximum(1);
//
//        barChart1.setData(data1);
//        float barSpace1 = 0.05f;
//        float groupSpace1 = 0.85f;
//        data1.setBarWidth(0.5f);
//
//        barChart1.getXAxis().setAxisMinimum(0);
//        barChart1.getXAxis().setAxisMaximum(0+barChart1.getBarData().getGroupWidth(groupSpace1, barSpace1)*3);
//        barChart1.getAxisLeft().setAxisMinimum(0);
//
//        barChart1.invalidate();
//
//        // barchart 3: Tong chi
//        Log.i("Báo cáo: ", "Tổng chi");
//        barChart2 = mView.findViewById(R.id.chart2);
//        BarDataSet barDataSet4 = new BarDataSet(barEntries3(), "Chi");
//        barDataSet4.setColor(Color.RED);
//
////        BarData data1 = new BarData();
//        data1.addDataSet(barDataSet3);
//
////        String[] content = new String[]{"Hốt hụi", "A trả nợ", "Lương"};
//        xAxis = barChart2.getXAxis();
//        xAxis.setValueFormatter(new IndexAxisValueFormatter(content));
//        xAxis.setCenterAxisLabels(true);
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setGranularity(1);
//        xAxis.setGranularityEnabled(true);
//
//        barChart2.setDragEnabled(true);
//        barChart2.setVisibleXRangeMaximum(1);
//
//        barChart2.setData(data1);
//        data1.setBarWidth(0.5f);
//
//        barChart2.getXAxis().setAxisMinimum(0);
//        barChart2.getXAxis().setAxisMaximum(0+barChart2.getBarData().getGroupWidth(groupSpace1, barSpace1)*3);
//        barChart2.getAxisLeft().setAxisMinimum(0);
//
//        barChart2.invalidate();
//        Log.i("Báo cáo: ", "invalidate");
//    }
//
//    private ArrayList<BarEntry> barEntries1(){
////        DuaDuLieuVaoSoDo();
//        ArrayList<BarEntry> barEntries = new ArrayList<>();
////        for (int i = 0; i < transactionList.size(); i++){
////            for (int j = 1; i <= transactionList.size(); j++){
////                if (transactionList.get(i).Type == "Thu"){
////                    barEntries.add(new BarEntry(j, transactionList.get(i).Amount));
////                }
////            }
////        }
//        barEntries.add(new BarEntry(1, 1000000));
//        barEntries.add(new BarEntry(2, 3500000));
//        barEntries.add(new BarEntry(3, 2500000));
//        barEntries.add(new BarEntry(4, 7000000));
//        return barEntries;
//    }
//
//    private ArrayList<BarEntry> barEntries2(){
////        DuaDuLieuVaoSoDo();
//        ArrayList<BarEntry> barEntries = new ArrayList<>();
////        for (int i = 0; i < transactionList.size(); i++){
////            for (int j = 1; i <= transactionList.size(); j++){
////                if (transactionList.get(i).Type == "Chi"){
////                barEntries.add(new BarEntry(j, transactionList.get(i).Amount));
////                }
////            }
////        }
//        barEntries.add(new BarEntry(1, 1000000));
//        barEntries.add(new BarEntry(2, 2000000));
//        barEntries.add(new BarEntry(3, 4000000));
//        barEntries.add(new BarEntry(4, 5500000));
//        return barEntries;
//    }
//
//    private ArrayList<BarEntry> barEntries3() {
//        ArrayList<BarEntry> barEntries = new ArrayList<>();
//        barEntries.add(new BarEntry(1, 1000000));
//        barEntries.add(new BarEntry(2, 2000000));
//        barEntries.add(new BarEntry(3, 4000000));
//        return barEntries;
//    }
//
//    @Override
//    public void onResume(){
//        super.onResume();
//        // Load lại sơ đồ ở đây.
//    }
//
//    private void LayDuLieuTransactions(){
//        // Lay du lieu tu firestore
//        sharedpreferences = mView.getContext().getSharedPreferences("LoginPreferences", Context.MODE_PRIVATE); // Chọn file có tên "LoginPreferences"
//        String logged_Email = sharedpreferences.getString("Email", "");  // Lấy địa chỉ email đã đăng nhập vào ứng dụng.
//
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        db.collection("Transactions")  // Chọn Bảng
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        // Chỗ này nó sẽ gọi nếu lấy dữ liệu thành công, làm mấy cái bla bla trong này
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {  // Duyệt từng cột trong bảng
//                                String email = document.getString("Email"); // Lấy field có tên là email.
//                                String amount = document.getString("Quantity");
//                                int quantity = Integer.parseInt(amount);
//                                String date = document.getString("Date");
//                                String type = document.getString("Type");
//                                String typeName = document.getString("TypeName");
//                                if(email == null)
//                                    continue;
//                                if(!email.equals(logged_Email))  // Không phải giao dịch của email này thì bỏ
//                                    continue;
//                                transactionList.add(new TransactionModel(date, email,"", quantity, "", type, typeName, ""));
//                                Log.i("Dữ liệu lấy được từ báo cáo: ", document.toString());
//                            }
//                            DuaDuLieuVaoSoDo();
//                        }
//
//                    }
//                });
//    }
//}

package com.example.money_management.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.money_management.R;
import com.example.money_management.activities.String2Currency;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class ReportDynamicFragment extends Fragment {

    BarChart barChart, barChart1, barChart2;
    View mView;
    ArrayList<TransactionModel> transactionList;
    String2Currency convert = new String2Currency();
    private SharedPreferences sharedpreferences;
    private TextView txtTongThu;
    private TextView txtTongChi;
    private TextView txtThuNhapRong;
    private TextView txtSoDuDau;
    private TextView txtSoDuCuoi;
    private Integer TNRThu;
    private Integer TNRChi;
    private Integer SoDuDau = 0;
    private Integer SoDuCuoi = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_dynamic_report, container, false);
        barChart = mView.findViewById(R.id.chart);

        txtSoDuCuoi = mView.findViewById(R.id.txtSoDuCuoi);
        txtSoDuDau = mView.findViewById(R.id.txtSoDuDau);
        txtTongThu = mView.findViewById(R.id.txtTongThu);
        txtTongChi = mView.findViewById(R.id.txtTongChi);
        txtThuNhapRong = mView.findViewById(R.id.txt_ThuNhapRong);
        transactionList = new ArrayList<>();
        String month = String.valueOf(getArguments().getInt("Month", 1));
        LayDuLieuTransactions(month);
        txtTongThu.setTextColor(Color.rgb(0, 178, 18));
        return mView;
    }

    private void UpdateSoDu(int month){
        sharedpreferences = mView.getContext().getSharedPreferences("LoginPreferences", Context.MODE_PRIVATE); // Chọn file có tên "LoginPreferences"
        String logged_Email = sharedpreferences.getString("Email", "");  // Lấy địa chỉ email đã đăng nhập vào ứng dụng.

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Transactions")  // Chọn Bảng
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        // Chỗ này nó sẽ gọi nếu lấy dữ liệu thành công, làm mấy cái bla bla trong này
                        if (task.isSuccessful()) {
                            Integer sum1 = 0; // Các tháng trước
                            Integer sum2 = 0; // Tháng hiện tại
                            for (QueryDocumentSnapshot document : task.getResult()) {  // Duyệt từng cột trong bảng
                                String email = document.getString("Email"); // Lấy field có tên là email.
                                String amount = document.getString("Quantity");
                                int quantity = Integer.parseInt(amount);
                                String date = document.getString("Date");
                                String type = document.getString("Type");
                                String typeName = document.getString("TypeName");
                                if (email == null)
                                    continue;
                                if (!email.equals(logged_Email))  // Không phải giao dịch của email này thì bỏ
                                    continue;
                                if(Integer.valueOf(date.split("/")[1]) == Integer.valueOf(month)){
                                    if(type.equals("Thu"))
                                        sum2 += quantity;
                                    else
                                        sum2 -= quantity;
                                }
                                if(Integer.valueOf(date.split("/")[1]) < Integer.valueOf(month)){
                                    if(type.equals("Thu"))
                                        sum1 += quantity;
                                    else
                                        sum1 -= quantity;
                                }
                            }

                            txtSoDuDau.setText(convert.convertString2Currency(String.valueOf(sum1)));
                            txtSoDuCuoi.setText(convert.convertString2Currency(String.valueOf(sum1 + sum2)));
                            if(sum1 <= sum2)
                                txtSoDuCuoi.setTextColor(Color.rgb(0, 178, 18));
                            else
                                txtSoDuCuoi.setTextColor(Color.rgb(246, 0,0 ));

                        }
                    }
                });
    }

    private void HienThiBaoCao(String month){
        TNRChi = 0;
        TNRThu = 0;
        //barchart 1: Thu nhap rong
        //Collections.sort(transactionList, (a, b) -> (Integer.valueOf(a.Date.split("/")[1]) < Integer.valueOf(b.Date.split("/")[1])) ? 1 : -1);
        BarDataSet barDataSet1 = new BarDataSet(barEntries1(), "Thu");
        barDataSet1.setColor(Color.GREEN);
        BarDataSet barDataSet2 = new BarDataSet(barEntries2(), "Chi");
        barDataSet2.setColor(Color.RED);

        barChart.notifyDataSetChanged();
        BarData data = new BarData(barDataSet1, barDataSet2);
        data.notifyDataChanged();
        barChart.setData(data);

        String[] date = new String[]{"1/" + month + " - 7/"  + month,
                                    "8/" + month + " - 14/"  + month,
                                    "15/" + month + " - 21/"  + month,
                                    "22/" + month + " - 28/"  + month,
                                    "29/" + month + " - 31/"  + month};
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(date));
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);
        xAxis.setGranularityEnabled(true);

        barChart.setDragEnabled(true);
        barChart.setVisibleXRangeMaximum(2);

        float barSpace = 0.05f;
        float groupSpace = 0.2f;
        data.setBarWidth(0.35f);

        barChart.getXAxis().setAxisMinimum(0);
        barChart.getXAxis().setAxisMaximum(0 + barChart.getBarData().getGroupWidth(groupSpace, barSpace) * 5);
        barChart.getAxisLeft().setAxisMinimum(0);

        barChart.groupBars(0, groupSpace, barSpace);

        barChart.invalidate();


        // barchart 2: Tong thu
        ArrayList<String> typeNameListFirst = new ArrayList<>();

        // Lấy các loại thu.
        for (int i = 0; i < transactionList.size(); i++) {
            if (!transactionList.get(i).Type.equals("Thu"))
                continue;
            typeNameListFirst.add(transactionList.get(i).TypeName);
        }

        // Xóa các loại trùng.
        ArrayList<String> typeNameList = removeDuplicates(typeNameListFirst);
        ArrayList<Integer> amountList = new ArrayList<>();

        for (int i =0; i < typeNameList.size(); i++){
            String typeNameItem = typeNameList.get(i);
            Integer sumAmount = 0;
            for(int j = 0; j < transactionList.size(); j++){
                String type = transactionList.get(j).Type;
                String typeName = transactionList.get(j).TypeName;
                Integer amount = transactionList.get(j).Amount;
                if(!type.equals("Thu"))
                    continue;
                if(!typeName.equals(typeNameItem))
                    continue;
                sumAmount += amount;
            }
            amountList.add(sumAmount);
        }
        barChart1 = mView.findViewById(R.id.chart1);
        BarDataSet barDataSet3 = new BarDataSet(barEntries3(amountList), "Thu");
        barDataSet3.setColor(Color.GREEN);
//
//        barChart1.notifyDataSetChanged();
        BarData data1 = new BarData();
        data1.notifyDataChanged();
        data1.addDataSet(barDataSet3);


        XAxis xAxis2 = barChart.getXAxis();
        xAxis2 = barChart1.getXAxis();
        xAxis2.setValueFormatter(new IndexAxisValueFormatter(typeNameList));
        xAxis2.setCenterAxisLabels(true);
        xAxis2.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis2.setGranularity(1);
        xAxis2.setGranularityEnabled(true);

        barChart1.setDragEnabled(true);
        barChart1.setVisibleXRangeMaximum(1);

        barChart1.setData(data1);
        float barSpace1 = 0.05f;
        float groupSpace1 = 0.85f;
        data1.setBarWidth(0.5f);

        barChart1.getXAxis().setAxisMinimum(0);
        barChart1.getXAxis().setAxisMaximum(0 + barChart1.getBarData().getGroupWidth(groupSpace1, barSpace1) * typeNameList.size());
        barChart1.getAxisLeft().setAxisMinimum(0);

//        barChart1.groupBars(0, groupSpace1, barSpace1);
        barChart1.invalidate();


        // barchart 3: Tong chi

        ArrayList<String> typeNameListFirst1 = new ArrayList<>();

        // Lấy các loại chi.
        for (int i = 0; i < transactionList.size(); i++) {
            if (!transactionList.get(i).Type.equals("Chi"))
                continue;
            typeNameListFirst1.add(transactionList.get(i).TypeName);
        }

        // Xóa các loại trùng.
        ArrayList<String> typeNameList1 = removeDuplicates(typeNameListFirst1);
        ArrayList<Integer> amountList1 = new ArrayList<>();

        for (int i =0; i < typeNameList1.size(); i++){
            String typeNameItem = typeNameList1.get(i);
            Integer sumAmount1 = 0;
            for(int j = 0; j < transactionList.size(); j++){
                String type = transactionList.get(j).Type;
                String typeName = transactionList.get(j).TypeName;
                Integer amount = transactionList.get(j).Amount;
                if(!type.equals("Chi"))
                    continue;
                if(!typeName.equals(typeNameItem))
                    continue;
                sumAmount1 += amount;
            }
            amountList1.add(sumAmount1);
        }
        barChart2 = mView.findViewById(R.id.chart2);
        BarDataSet barDataSet4 = new BarDataSet(barEntries4(amountList1), "Chi");
        barDataSet4.setColor(Color.RED);

        BarData data2 = new BarData();
        data2.notifyDataChanged();
        data2.addDataSet(barDataSet4);
        barChart2.notifyDataSetChanged();
        data2.notifyDataChanged();

//        String[] content = new String[]{"Hốt hụi", "A trả nợ", "Lương"};
        XAxis xAxis3 = barChart.getXAxis();
        xAxis3 = barChart2.getXAxis();
        xAxis3.setValueFormatter(new IndexAxisValueFormatter(typeNameList1));
        xAxis3.setCenterAxisLabels(true);
        xAxis3.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis3.setGranularity(1);
        xAxis3.setGranularityEnabled(true);

        barChart2.setDragEnabled(true);
        barChart2.setVisibleXRangeMaximum(1);

        barChart2.setData(data2);
        data2.setBarWidth(0.5f);

        barChart2.getXAxis().setAxisMinimum(0);
        barChart2.getXAxis().setAxisMaximum(0 + barChart2.getBarData().getGroupWidth(groupSpace1, barSpace1) * typeNameList1.size());
        barChart2.getAxisLeft().setAxisMinimum(0);

        barChart2.invalidate();

        txtThuNhapRong.setText(convert.convertString2Currency(String.valueOf(TNRThu - TNRChi)));
        if(Integer.valueOf(TNRThu - TNRChi) >= 0)
            txtThuNhapRong.setTextColor(Color.rgb(0, 178, 18));
        else
            txtThuNhapRong.setTextColor(Color.rgb(246, 0,0 ));
        UpdateSoDu(Integer.valueOf(month));
    }

    private ArrayList<BarEntry> barEntries1() {
        Log.i("Đang thêm dữ liệu vào báo cáo", "Thu");
        ArrayList<BarEntry> barEntries = new ArrayList<>();

        Integer sum17 = 0;
        Integer sum814 = 0;
        Integer sum1521 = 0;
        Integer sum2228 = 0;
        Integer sum2931 = 0;
        for (int i = 0; i < transactionList.size(); i++) {
            if (transactionList.get(i).Type.equals("Thu")) {
                Integer date = Integer.parseInt(transactionList.get(i).Date.split("/")[0]);
                Integer amount = transactionList.get(i).Amount;
                if(date <= 7)
                    sum17 += amount;
                if(date > 7 && date <= 14)
                    sum814 += amount;
                if(date >14 && date <=21)
                    sum1521 += amount;
                if(date >21 && date <=28)
                    sum2228 +=amount;
                if(date >28)
                    sum2931 +=amount;
                TNRThu += amount;
            }
            Log.i("Báo cáo thu: ", sum17 + " " + sum814+ " " + sum1521+ " " + sum2228+ " " + sum2931);
        }
        barEntries.add(new BarEntry(1, 0 + sum17));
        barEntries.add(new BarEntry(2, 0 + sum814));
        barEntries.add(new BarEntry(3, 0 + sum1521));
        barEntries.add(new BarEntry(4, 0 + sum2228));
        barEntries.add(new BarEntry(5, 0 + sum2931));
        txtTongThu.setText(convert.convertString2Currency(String.valueOf(sum17 + sum814 + sum1521 + sum2228 + sum2931)));
        return barEntries;
    }

    private ArrayList<BarEntry> barEntries2() {
        Log.i("Đang thêm dữ liệu vào báo cáo", "Chi");
        ArrayList<BarEntry> barEntries = new ArrayList<>();
//        int j = 1;
//        for (int i = 0; i < transactionList.size(); i++) {
//                if (transactionList.get(i).Type.equals("Chi")) {
//                    int y = transactionList.get(i).Amount;
//                    Log.i("Báo cáo chi: ", j + " " + y);
//                    barEntries.add(new BarEntry(0 + j, 0 + y));
//                    j++;
//                }
//        }
        Integer sum17 = 0;
        Integer sum814 = 0;
        Integer sum1521 = 0;
        Integer sum2228 = 0;
        Integer sum2931 = 0;
        for (int i = 0; i < transactionList.size(); i++) {
            if (transactionList.get(i).Type.equals("Chi")) {
                Integer date = Integer.parseInt(transactionList.get(i).Date.split("/")[0]);
                Integer amount = transactionList.get(i).Amount;
                if(date <= 7)
                    sum17 += amount;
                if(date > 7 && date <= 14)
                    sum814 += amount;
                if(date >14 && date <=21)
                    sum1521 += amount;
                if(date >21 && date <=28)
                    sum2228 +=amount;
                if(date >28)
                    sum2931 +=amount;
                TNRChi += amount;
            }
            Log.i("Báo cáo chi: ", sum17 + " " + sum814+ " " + sum1521+ " " + sum2228+ " " + sum2931);
        }
        barEntries.add(new BarEntry(1, 0 + sum17));
        barEntries.add(new BarEntry(2, 0 + sum814));
        barEntries.add(new BarEntry(3, 0 + sum1521));
        barEntries.add(new BarEntry(4, 0 + sum2228));
        barEntries.add(new BarEntry(5, 0 + sum2931));
        txtTongChi.setText(convert.convertString2Currency(String.valueOf(sum17 + sum814 + sum1521 + sum2228 + sum2931)));
        return barEntries;
    }

    private ArrayList<BarEntry> barEntries3(ArrayList<Integer> amountList) {
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        int j = 1;
        for (int i = 0; i < amountList.size(); i++) {
                    barEntries.add(new BarEntry(0 + j, 0 + amountList.get(i)));
                    j++;
        }
        return barEntries;
    }

    private ArrayList<BarEntry> barEntries4(ArrayList<Integer> amountList) {
//        ArrayList<BarEntry> barEntries = new ArrayList<>();
//        int j = 1;
//        for (int i = 0; i < transactionList.size(); i++) {
//                if (transactionList.get(i).Type.equals("Chi")) {
//                    int y = transactionList.get(i).Amount;
//                    Log.i("Báo cáo chi: ", j + " " + y);
//                    barEntries.add(new BarEntry(0 + j, 0 + y));
//                    j++;
//                }
//        }
//        return barEntries;
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        int j = 1;
        for (int i = 0; i < amountList.size(); i++) {
            barEntries.add(new BarEntry(0 + j, 0 + amountList.get(i)));
            j++;
        }
        return barEntries;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Load lại sơ đồ ở đây.
        String month = String.valueOf(getArguments().getInt("Month", 1));
        LayDuLieuTransactions(month);
    }

    private void LayDuLieuTransactions(String month) {
        // Lay du lieu tu firestore
        sharedpreferences = mView.getContext().getSharedPreferences("LoginPreferences", Context.MODE_PRIVATE); // Chọn file có tên "LoginPreferences"
        String logged_Email = sharedpreferences.getString("Email", "");  // Lấy địa chỉ email đã đăng nhập vào ứng dụng.

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Transactions")  // Chọn Bảng
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        // Chỗ này nó sẽ gọi nếu lấy dữ liệu thành công, làm mấy cái bla bla trong này
                        if (task.isSuccessful()) {
                            transactionList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {  // Duyệt từng cột trong bảng
                                String email = document.getString("Email"); // Lấy field có tên là email.
                                String amount = document.getString("Quantity");
                                int quantity = Integer.parseInt(amount);
                                String date = document.getString("Date");
                                String type = document.getString("Type");
                                String typeName = document.getString("TypeName");
                                if (email == null)
                                    continue;
                                if (!email.equals(logged_Email))  // Không phải giao dịch của email này thì bỏ
                                    continue;
                                if(date.split("/")[1].equals(month))

                                transactionList.add(new TransactionModel(email, date, "", quantity, "", type, typeName, ""));
//                                Log.i("Dữ liệu lấy được từ báo cáo: ", document.toString());
                                Log.i("Dữ liệu lấy được từ báo cáo: ", amount + " " + date + " " + email);
                            }
                            HienThiBaoCao(month);
                        }

                    }
                });
    }
    public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list)
    {

        // Create a new ArrayList
        ArrayList<T> newList = new ArrayList<T>();

        // Traverse through the first list
        for (T element : list) {

            // If this element is not present in newList
            // then add it
            if (!newList.contains(element)) {

                newList.add(element);
            }
        }

        // return the new list
        return newList;
    }
}