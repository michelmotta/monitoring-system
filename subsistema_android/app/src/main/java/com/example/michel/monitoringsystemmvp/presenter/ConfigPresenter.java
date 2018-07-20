package com.example.michel.monitoringsystemmvp.presenter;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.michel.monitoringsystemmvp.interfaces.ConfigInterface;

import org.json.JSONException;
import org.json.JSONObject;

public class ConfigPresenter implements ConfigInterface.Presenter{

    private ConfigInterface.View view;
    private RequestQueue queue;
    //private String ipAdress = "http://10.0.2.2";
    //private String ipAdress = "http://10.87.7.210";
    private String ipAdress = "http://10.87.9.196";

    public ConfigPresenter(ConfigInterface.View view) {
        this.view = view;
    }

    @Override
    public void requestApiConfigData() {
        String URL = ipAdress + ":8080/monitoring-system/service/config/view";

        if (queue == null) {
            queue = Volley.newRequestQueue(view.getContext());
        }

        JsonObjectRequest configDataRequest = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.i("LOG_VOLLEY", jsonObject.toString());
                        try {
                            String responseStatus = jsonObject.getString("status");
                            String responseMessage = jsonObject.getString("message");

                            if(responseStatus.equals("success")){
                                JSONObject config = jsonObject.getJSONObject("config");

                                String configMaxTempLim = config.getString("maxTemp");
                                String configMinTempLim = config.getString("minTemp");
                                String configMaxHumLim = config.getString("maxHum");
                                String configMinHumLim = config.getString("minHum");
                                String configReadingRange = config.getString("range");

                                view.setConfigData(configMaxTempLim, configMinTempLim, configMaxHumLim, configMinHumLim, configReadingRange);
                            }else{

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e("Config Error", volleyError.toString());
                    }
                });
        queue.add(configDataRequest);
    }

    @Override
    public void editApiSystemSettings(String configMaxTempLim, String configMinTempLim, String configMaxHumLim, String configMinHumLim, String configReadingRange) {

        String URL = ipAdress + ":8080/monitoring-system/service/config/edit";

        JSONObject settings = new JSONObject();
        try{
            settings.put("id", 1);
            settings.put("maximumTemperatureLimit", configMaxTempLim);
            settings.put("minimumTemperatureLimit", configMinTempLim);
            settings.put("maximumHumidityLimit", configMaxHumLim);
            settings.put("minimumHumidityLimit", configMinHumLim);
            settings.put("readingRangeTime", configReadingRange);
        }catch (JSONException e){
            e.printStackTrace();
        }

        queue = Volley.newRequestQueue(view.getContext());

        JsonObjectRequest settingsEdit = new JsonObjectRequest(Request.Method.PUT, URL, settings,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.i("LOG_VOLLEY", jsonObject.toString());
                        try {
                            String responseStatus = jsonObject.getString("status");
                            String responseMessage = jsonObject.getString("message");

                            if(responseStatus.equals("success")){
                                view.settingsEditFeedbackMessageSuccess(responseMessage);
                            }else{
                                view.settingsEditFeedbackMessageError(responseMessage);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e("Config Error", volleyError.toString());
                    }
                });
        queue.add(settingsEdit);
    }
}
