package com.example.michel.monitoringsystemmvp.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.michel.monitoringsystemmvp.R;
import com.example.michel.monitoringsystemmvp.interfaces.HistoricInterface;
import com.example.michel.monitoringsystemmvp.model.Temperature;
import com.example.michel.monitoringsystemmvp.presenter.HistoricPresenter;
import com.example.michel.monitoringsystemmvp.utils.TemperatureListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoricActivity extends AppCompatActivity implements HistoricInterface.View{

    @BindView(R.id.mainToolbar)
    Toolbar mainToolbar;

    @BindView(R.id.recyclerViewTemperature)
    RecyclerView recyclerViewTemperature;

    private RecyclerView.Adapter adapter;
    private ProgressDialog progressDialog;
    private HistoricInterface.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historic);

        ButterKnife.bind(this);

        mainToolbar.setTitle(this.getResources().getString(R.string.appPageTitleHistoric));

        setSupportActionBar(mainToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new HistoricPresenter(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(this.getResources().getString(R.string.appLoading));
        progressDialog.show();

        presenter.generateTemperatureListView();

    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void renderTemperatureList(List<Temperature> temperatureList) {
        recyclerViewTemperature.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TemperatureListAdapter(temperatureList, this);
        recyclerViewTemperature.setAdapter(adapter);

        progressDialog.dismiss();
    }
}
