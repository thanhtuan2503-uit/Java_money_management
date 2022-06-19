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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.money_management.R;
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
import java.util.Objects;

public class ReportDynamicFragment extends Fragment {

    BarChart barChart, barChart1, barChart2;
    View mView;
    ArrayList<TransactionModel> transactionList;
    private SharedPreferences sharedpreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_dynamic_report, container, false);
        barChart = mView.findViewById(R.id.chart);

        transactionList = new ArrayList<>();
        LayDuLieuTransactions();

        return mView;
    }

    private void HienThiBaoCao(){
        //barchart 1: Thu nhap rong
        BarDataSet barDataSet1 = new BarDataSet(barEntries1(), "Thu");
        barDataSet1.setColor(Color.GREEN);
        BarDataSet barDataSet2 = new BarDataSet(barEntries2(), "Chi");
        barDataSet2.setColor(Color.RED);

        BarData data = new BarData(barDataSet1, barDataSet2);
        barChart.setData(data);

        String[] date = new String[]{"1/2 - 7/2", "8/2 - 14/2", "15/2 - 21/2", "22/2 - 28/2", "29/2 - 31/2"};
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
        // barchart 2
        barChart1 = mView.findViewById(R.id.chart1);
        BarDataSet barDataSet3 = new BarDataSet(barEntries3(), "Thu");
        barDataSet3.setColor(Color.GREEN);

        BarData data1 = new BarData();
        data1.addDataSet(barDataSet3);

        String[] content = new String[]{"Hốt hụi", "A trả nợ", "Lương"};
        xAxis = barChart1.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(content));
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);
        xAxis.setGranularityEnabled(true);

        barChart1.setDragEnabled(true);
        barChart1.setVisibleXRangeMaximum(1);

        barChart1.setData(data1);
        float barSpace1 = 0.05f;
        float groupSpace1 = 0.85f;
        data1.setBarWidth(0.5f);

        barChart1.getXAxis().setAxisMinimum(0);
        barChart1.getXAxis().setAxisMaximum(0 + barChart1.getBarData().getGroupWidth(groupSpace1, barSpace1) * 3);
        barChart1.getAxisLeft().setAxisMinimum(0);

//        barChart1.groupBars(0, groupSpace1, barSpace1);
        barChart1.invalidate();
        // barchart 3: Tong chi
        barChart2 = mView.findViewById(R.id.chart2);
        BarDataSet barDataSet4 = new BarDataSet(barEntries3(), "Chi");
        barDataSet4.setColor(Color.RED);

//        BarData data1 = new BarData();
        data1.addDataSet(barDataSet3);

//        String[] content = new String[]{"Hốt hụi", "A trả nợ", "Lương"};
        xAxis = barChart2.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(content));
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);
        xAxis.setGranularityEnabled(true);

        barChart2.setDragEnabled(true);
        barChart2.setVisibleXRangeMaximum(1);

        barChart2.setData(data1);
        data1.setBarWidth(0.5f);

        barChart2.getXAxis().setAxisMinimum(0);
        barChart2.getXAxis().setAxisMaximum(0 + barChart2.getBarData().getGroupWidth(groupSpace1, barSpace1) * 3);
        barChart2.getAxisLeft().setAxisMinimum(0);

        barChart2.invalidate();
    }

    private ArrayList<BarEntry> barEntries1() {
        Log.i("Đang thêm dữ liệu vào báo cáo", "Thu");
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        for (int i = 0; i < transactionList.size(); i++) {
                float j = i+1;
                if (transactionList.get(i).Type.equals("Thu")) {
                    float y = Float.parseFloat(String.valueOf(transactionList.get(i).Amount));

                    Log.i("Báo cáo thu: ", j + " " + String.valueOf(y));
                    barEntries.add(new BarEntry(0 + j, 0 + y + i));
            }
        }
//        barEntries.add(new BarEntry(1, 1000000));
//        barEntries.add(new BarEntry(2, 3500000));
//        barEntries.add(new BarEntry(3, 2500000));
//        barEntries.add(new BarEntry(4, 7000000));
        return barEntries;
    }

    private ArrayList<BarEntry> barEntries2() {
        Log.i("Đang thêm dữ liệu vào báo cáo", "Chi");
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        for (int i = 0; i < transactionList.size(); i++) {
            float j = i+1;
                if (transactionList.get(i).Type.equals("Chi")) {
                    float y = Float.parseFloat(String.valueOf(transactionList.get(i).Amount));
                    Log.i("Báo cáo chi: ", j + " " + y);
                    barEntries.add(new BarEntry(0+j, 0+y + i));
                }
        }
//        barEntries.add(new BarEntry(1, 1000000));
//        barEntries.add(new BarEntry(2, 2000000));
//        barEntries.add(new BarEntry(3, 4000000));
//        barEntries.add(new BarEntry(4, 5500000));
        return barEntries;
    }

    private ArrayList<BarEntry> barEntries3() {
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        for (int i = 0; i < transactionList.size(); i++) {
            float j = i+1;
                if (transactionList.get(i).Type.equals("Thu")) {
                    float y = Float.parseFloat(String.valueOf(transactionList.get(i).Amount));
                    Log.i("Báo cáo thu3: ", j + " " + y);
                    barEntries.add(new BarEntry(0+j, 0+y + i));
            }
        }
//        barEntries.add(new BarEntry(1, 1000000));
//        barEntries.add(new BarEntry(2, 2000000));
//        barEntries.add(new BarEntry(3, 4000000));
        return barEntries;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Load lại sơ đồ ở đây.
    }

    private void LayDuLieuTransactions() {
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
                                transactionList.add(new TransactionModel(date, email, "", quantity, "", type, typeName, ""));
//                                Log.i("Dữ liệu lấy được từ báo cáo: ", document.toString());
                                Log.i("Dữ liệu lấy được từ báo cáo: ", amount + " " + date + " " + email);
                            }
                            HienThiBaoCao();
                        }

                    }
                });
    }
}