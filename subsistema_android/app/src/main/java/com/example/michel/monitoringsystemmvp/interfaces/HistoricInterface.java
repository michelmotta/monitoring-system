package com.example.michel.monitoringsystemmvp.interfaces;

import android.content.Context;

import com.example.michel.monitoringsystemmvp.model.Temperature;

import java.util.List;

public interface HistoricInterface {

    interface View{

        Context getContext();

        void renderTemperatureList(List<Temperature> temperatureList);
    }

    interface Presenter{

        void generateTemperatureListView();
    }

    interface Model{

    }
}
