package com.example.michel.monitoringsystemmvp.presenter;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.michel.monitoringsystemmvp.interfaces.GraphInterface;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GraphPresenter implements GraphInterface.Presenter{

    private GraphInterface.View view;
    private RequestQueue queue;
    //private String ipAdress = "http://10.0.2.2";
    //private String ipAdress = "http://10.87.7.210";
    private String ipAdress = "http://10.87.9.196";


    public GraphPresenter(GraphInterface.View view) {
        this.view = view;
    }

    @Override
    public void requestApiDateGraph(final LineChart mChart) {

        String URL = ipAdress + ":8080/monitoring-system/service/temperature/graph/5";

        if (queue == null) {
            queue = Volley.newRequestQueue(view.getContext());
        }

        JsonArrayRequest configDataRequest = new JsonArrayRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {

                        if(jsonArray.length() == 15){

                            ArrayList<Entry> yTempServer1 = new ArrayList<>();
                            ArrayList<Entry> yTempServer2 = new ArrayList<>();
                            ArrayList<Entry> yTempServer3 = new ArrayList<>();

                            ArrayList<Entry> yHumServer1 = new ArrayList<>();
                            ArrayList<Entry> yHumServer2 = new ArrayList<>();
                            ArrayList<Entry> yHumServer3 = new ArrayList<>();

                            final ArrayList<String> xLabel = new ArrayList<>();
                            XAxis xAxis = mChart.getXAxis();
                            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                            xAxis.setDrawGridLines(false);
                            xAxis.setGranularity(1f);
                            xAxis.setValueFormatter(new IAxisValueFormatter() {
                                @Override
                                public String getFormattedValue(float value, AxisBase axis) {
                                    return xLabel.get((int)value);
                                }
                            });

                            for(int i = 0; i < jsonArray.length(); i++){
                                try {
                                    JSONObject temperatureJson = jsonArray.getJSONObject(i);

                                    int serverId = temperatureJson.getInt("serverId");

                                    if(serverId == 1){
                                        Float  tempValue = Float.parseFloat(temperatureJson.getString("tempValue"));
                                        Float  humValue = Float.parseFloat(temperatureJson.getString("humValue"));

                                        yTempServer1.add(new Entry((float) i, tempValue));
                                        yHumServer1.add(new Entry((float) i, humValue));
                                    }

                                    if(serverId == 2){
                                        Float  tempValue = Float.parseFloat(temperatureJson.getString("tempValue"));
                                        Float  humValue = Float.parseFloat(temperatureJson.getString("humValue"));

                                        yTempServer2.add(new Entry((float) (i-5), tempValue));
                                        yHumServer2.add(new Entry((float) (i-5), humValue));
                                    }

                                    if(serverId == 3){
                                        Float  tempValue = Float.parseFloat(temperatureJson.getString("tempValue"));
                                        Float  humValue = Float.parseFloat(temperatureJson.getString("humValue"));

                                        yTempServer3.add(new Entry((float) (i-10), tempValue));
                                        yHumServer3.add(new Entry((float) (i-10), humValue));
                                    }

                                    xLabel.add(String.valueOf(i));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            view.renderDateGraph(yTempServer1, yTempServer2, yTempServer3, yHumServer1, yHumServer2, yHumServer3);


                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e("Graph Error", volleyError.toString());
                    }
                });
        queue.add(configDataRequest);

    }


}
