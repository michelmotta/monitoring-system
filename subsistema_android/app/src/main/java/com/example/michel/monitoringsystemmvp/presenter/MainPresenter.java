package com.example.michel.monitoringsystemmvp.presenter;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.michel.monitoringsystemmvp.utils.UserSession;
import com.example.michel.monitoringsystemmvp.interfaces.MainInterface;

import org.json.JSONException;
import org.json.JSONObject;

public class MainPresenter implements MainInterface.Presenter{

    private MainInterface.View view;
    private UserSession userSession;
    private RequestQueue queue;
    //private String ipAdress = "http://10.0.2.2";
    //private String ipAdress = "http://10.87.7.210";
    private String ipAdress = "http://10.87.9.196";

    public MainPresenter(MainInterface.View view) {
        this.view = view;
        userSession = new UserSession(view.getContext());
    }

    @Override
    public void logoutUser() {
        userSession.setLoggedIn(false, null);
    }

    @Override
    public void requestApiTemperatureStatus() {

        String URL = ipAdress + ":8080/monitoring-system/service/temperature/check/10";

        if (queue == null) {
            queue = Volley.newRequestQueue(view.getContext());
        }

        JsonObjectRequest configDataRequest = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        if(jsonObject.length() != 0){
                            try {
                                String averageTemperature = jsonObject.getString("average_temperature");
                                String averageHumidity = jsonObject.getString("average_humidity");

                                view.setAverageTemperatureHumidity(averageTemperature, averageHumidity);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e("Main Error", volleyError.toString());
                    }
                });
        queue.add(configDataRequest);

    }


}
