package com.example.michel.monitoringsystemmvp.interfaces;

import android.content.Context;

public interface ConfigInterface {

    interface View{

        Context getContext();

        void setConfigData(String configMaxTempLim, String configMinTempLim, String configMaxHumLim, String configMinHumLim, String configReadingRange);

        void settingsEditFeedbackMessageSuccess(String responseMessage);

        void settingsEditFeedbackMessageError(String responseMessage);
    }

    interface Presenter{

        void requestApiConfigData();

        void editApiSystemSettings(String configMaxTempLim, String configMinTempLim, String configMaxHumLim, String configMinHumLim, String configReadingRange);
    }

    interface Model{

    }

}
