package com.example.michel.monitoringsystemmvp.presenter;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.michel.monitoringsystemmvp.interfaces.HistoricInterface;
import com.example.michel.monitoringsystemmvp.model.Temperature;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HistoricPresenter implements HistoricInterface.Presenter{

    private HistoricInterface.View view;
    private RequestQueue queue;
    private List<Temperature> temperatureList;
    //private String ipAdress = "http://10.0.2.2";
    //private String ipAdress = "http://10.87.7.210";
    private String ipAdress = "http://10.87.9.196";

    public HistoricPresenter(HistoricInterface.View view) {
        this.view = view;
        temperatureList = new ArrayList<>();
    }

    @Override
    public void generateTemperatureListView() {

        String URL = ipAdress + ":8080/monitoring-system/service/temperature/all/10";

        if (queue == null) {
            queue = Volley.newRequestQueue(view.getContext());
        }

        JsonArrayRequest configDataRequest = new JsonArrayRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {

                        if(jsonArray.length() != 0){
                            for(int i = 0; i < jsonArray.length(); i++){
                                try {

                                    JSONObject temperatureJson = jsonArray.getJSONObject(i);
                                    Temperature temperature = new Temperature(
                                            temperatureJson.getInt("id"),
                                            temperatureJson.getInt("server_id"),
                                            temperatureJson.getString("server_name"),
                                            temperatureJson.getDouble("temperature"),
                                            temperatureJson.getDouble("humidity"),
                                            temperatureJson.getString("time")
                                    );
                                    temperatureList.add(temperature);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            view.renderTemperatureList(temperatureList);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e("Historic Error", volleyError.toString());
                    }
                });
        queue.add(configDataRequest);

    }

}
