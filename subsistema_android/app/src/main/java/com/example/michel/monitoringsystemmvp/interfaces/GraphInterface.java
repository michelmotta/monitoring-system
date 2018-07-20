package com.example.michel.monitoringsystemmvp.interfaces;

import android.content.Context;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;

public interface GraphInterface {

    interface View{

        Context getContext();

        void renderDateGraph(ArrayList<Entry> yTempServer1, ArrayList<Entry> yTempServer2, ArrayList<Entry> yTempServer3, ArrayList<Entry> yHumServer1, ArrayList<Entry> yHumServer2, ArrayList<Entry> yHumServer3);
    }

    interface Presenter{

        void requestApiDateGraph(LineChart mChart);
    }

    interface Model{

    }

}
