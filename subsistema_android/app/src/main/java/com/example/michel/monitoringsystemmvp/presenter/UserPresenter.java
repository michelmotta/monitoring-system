package com.example.michel.monitoringsystemmvp.presenter;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.michel.monitoringsystemmvp.utils.UserSession;
import com.example.michel.monitoringsystemmvp.interfaces.UserInterface;

import org.json.JSONException;
import org.json.JSONObject;

public class UserPresenter implements UserInterface.Presenter{

    private UserInterface.View view;
    private RequestQueue queue;
    private UserSession userSession;
    //private String ipAdress = "http://10.0.2.2";
    //private String ipAdress = "http://10.87.7.210";
    private String ipAdress = "http://10.87.9.196";

    public UserPresenter(UserInterface.View view) {
        this.view = view;
        userSession = new UserSession(view.getContext());
    }


    @Override
    public void requestApiUserInfo() {

        final String userEmail = userSession.getSessionEmail();
        String URL = ipAdress + ":8080/monitoring-system/service/user/details?email=" + userEmail;

        if (queue == null) {
            queue = Volley.newRequestQueue(view.getContext());
        }

        JsonObjectRequest userInfoRequest = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            String responseStatus = jsonObject.getString("status");
                            String responseMessage = jsonObject.getString("message");

                            if(responseStatus.equals("success")){
                                JSONObject userDetails = jsonObject.getJSONObject("user");
                                String userId = userDetails.getString("id");
                                String userUsername = userDetails.getString("username");
                                String userPassword = userDetails.getString("password");
                                String userEmail = userDetails.getString("email");
                                String userPhone = userDetails.getString("phone");

                                view.setUserinfos(userId, userUsername, userPassword, userEmail, userPhone);
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
                        Log.e("User Error", volleyError.toString());
                    }
                });
        queue.add(userInfoRequest);
    }

    @Override
    public void requestApiEditUserAccountInfo(String userId, String userUsername, String userPassword, String userEmail, String userPhone) {

        String URL = ipAdress + ":8080/monitoring-system/service/user/edit/" + userId;

        JSONObject jsonUser = new JSONObject();
        try{
            jsonUser.put("id", userId);
            jsonUser.put("username", userUsername);
            jsonUser.put("email", userEmail);
            jsonUser.put("password", userPassword);
            jsonUser.put("phone", userPhone);
        }catch (JSONException e){
            e.printStackTrace();
        }

        queue = Volley.newRequestQueue(view.getContext());
        JsonObjectRequest userInfoEdit = new JsonObjectRequest(Request.Method.PUT, URL, jsonUser,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.i("LOG_VOLLEY", jsonObject.toString());
                        try {
                            String responseStatus = jsonObject.getString("status");
                            String responseMessage = jsonObject.getString("message");

                            if(responseStatus.equals("success")){
                                view.userEditFeedbackMessageSuccess(responseMessage);
                            }else{
                                view.userEditFeedbackMessageError(responseMessage);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e("User Error", volleyError.toString());
                    }
                });
        queue.add(userInfoEdit);
    }
}
