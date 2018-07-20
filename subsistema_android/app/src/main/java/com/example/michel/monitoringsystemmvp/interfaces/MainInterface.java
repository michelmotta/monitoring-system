package com.example.michel.monitoringsystemmvp.interfaces;

import android.content.Context;

public interface MainInterface {

    interface View{
        Context getContext();

        void setAverageTemperatureHumidity(String averageTemperature, String averageHumidity);
    }

    interface Presenter{
        void logoutUser();

        void requestApiTemperatureStatus();
    }

    interface Model{}
}
