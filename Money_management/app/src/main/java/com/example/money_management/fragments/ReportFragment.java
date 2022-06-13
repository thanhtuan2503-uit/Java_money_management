package com.example.money_management.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.money_management.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;

public class ReportFragment extends Fragment {

    BarChart barChart, barChart1;
    View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_report, container, false);
        barChart = mView.findViewById(R.id.chart1);
        BarDataSet barDataSet1 = new BarDataSet(barEntries1(),"Thu");
        barDataSet1.setColor(Color.GREEN);
        BarDataSet barDataSet2 = new BarDataSet(barEntries2(),"Chi");
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
        barChart.getXAxis().setAxisMaximum(0+barChart.getBarData().getGroupWidth(groupSpace, barSpace)*5);
        barChart.getAxisLeft().setAxisMinimum(0);

        barChart.groupBars(0, groupSpace, barSpace);

        barChart.invalidate();
// barchart 2
        barChart1 = mView.findViewById(R.id.chart2);
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
        barChart1.getXAxis().setAxisMaximum(0+barChart1.getBarData().getGroupWidth(groupSpace1, barSpace1)*3);
        barChart1.getAxisLeft().setAxisMinimum(0);

//        barChart1.groupBars(0, groupSpace1, barSpace1);
        barChart1.invalidate();

        return mView;
    }

    private ArrayList<BarEntry> barEntries1(){
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(1, 1000000));
        barEntries.add(new BarEntry(2, 3500000));
        barEntries.add(new BarEntry(3, 2500000));
        barEntries.add(new BarEntry(4, 7000000));
        return barEntries;
    }

    private ArrayList<BarEntry> barEntries2(){
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(1, 1000000));
        barEntries.add(new BarEntry(2, 2000000));
        barEntries.add(new BarEntry(3, 4000000));
        barEntries.add(new BarEntry(4, 5500000));
        return barEntries;
    }

    private ArrayList<BarEntry> barEntries3() {
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(1, 1000000));
        barEntries.add(new BarEntry(2, 2000000));
        barEntries.add(new BarEntry(3, 4000000));
        return barEntries;
    }
}