package com.example.michel.monitoringsystemmvp.view;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.michel.monitoringsystemmvp.R;
import com.example.michel.monitoringsystemmvp.interfaces.GraphInterface;
import com.example.michel.monitoringsystemmvp.presenter.GraphPresenter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GraphActivity extends AppCompatActivity implements GraphInterface.View{

    @BindView(R.id.mainToolbar)
    Toolbar mainToolbar;

    @BindView(R.id.lineChart)
    LineChart mChart;

    private GraphInterface.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        ButterKnife.bind(this);

        mainToolbar.setTitle(this.getResources().getString(R.string.appPageTitleGraph));

        setSupportActionBar(mainToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new GraphPresenter(this);

        Legend legend = mChart.getLegend();
        legend.setTextSize(14f);
        mChart.animateX(1000);


        presenter.requestApiDateGraph(mChart);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void renderDateGraph(ArrayList<Entry> yTempServer1, ArrayList<Entry> yTempServer2, ArrayList<Entry> yTempServer3, ArrayList<Entry> yHumServer1, ArrayList<Entry> yHumServer2, ArrayList<Entry> yHumServer3) {
        LineDataSet setTempServer1, setTempServer2, setTempServer3, setHumServer1, setHumServer2, setHumServer3;

        setTempServer1 = new LineDataSet(yTempServer1, "Temp 1");
        setTempServer1.setColor(Color.BLUE);
        setTempServer1.setValueTextSize(13f);
        setTempServer1.setDrawCircles(false);
        setTempServer1.setLineWidth(3f);
        setTempServer1.setDrawValues(false);

        setTempServer2 = new LineDataSet(yTempServer2, "Temp 2");
        setTempServer2.setColor(Color.GREEN);
        setTempServer2.setValueTextSize(13f);
        setTempServer2.setDrawCircles(false);
        setTempServer2.setLineWidth(3f);
        setTempServer2.setDrawValues(false);

        setTempServer3 = new LineDataSet(yTempServer3, "Temp 3");
        setTempServer3.setColor(Color.MAGENTA);
        setTempServer3.setValueTextSize(13f);
        setTempServer3.setDrawCircles(false);
        setTempServer3.setLineWidth(3f);
        setTempServer3.setDrawValues(false);

        setHumServer1 = new LineDataSet(yHumServer1, "Hum 1");
        setHumServer1.setColor(Color.RED);
        setHumServer1.setValueTextSize(13f);
        setHumServer1.setDrawCircles(false);
        setHumServer1.setLineWidth(3f);
        setHumServer1.setDrawValues(false);

        setHumServer2 = new LineDataSet(yHumServer2, "Hum 2");
        setHumServer2.setColor(Color.YELLOW);
        setHumServer2.setValueTextSize(13f);
        setHumServer2.setDrawCircles(false);
        setHumServer2.setLineWidth(3f);
        setHumServer2.setDrawValues(false);

        setHumServer3 = new LineDataSet(yHumServer3, "Hum 3");
        setHumServer3.setColor(Color.GRAY);
        setHumServer3.setValueTextSize(13f);
        setHumServer3.setDrawCircles(false);
        setHumServer3.setLineWidth(3f);
        setHumServer3.setDrawValues(false);

        LineData data = new LineData(setTempServer1, setTempServer2, setTempServer3, setHumServer1, setHumServer2, setHumServer3);

        mChart.setData(data);
    }
}
