package com.example.michel.monitoringsystemmvp.presenter;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.michel.monitoringsystemmvp.utils.UserSession;
import com.example.michel.monitoringsystemmvp.interfaces.LoginInterface;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginPresenter implements LoginInterface.Presenter{

    private LoginInterface.View view;
    private RequestQueue queue;
    private UserSession userSession;
    //private String ipAdress = "http://10.0.2.2";
    //private String ipAdress = "http://10.87.7.210";
    private String ipAdress = "http://10.87.9.196";

    public LoginPresenter(LoginInterface.View view) {
        this.view = view;
        userSession = new UserSession(view.getContext());
    }

    @Override
    public void loginValidation(final String userEmail, final String userPassword){

        if(userEmail.isEmpty() || userPassword.isEmpty()){
            if(userEmail.isEmpty()){
                view.userEmailEmptyError();
            }
            if(userPassword.isEmpty()){
                view.userPasswordEmptyError();
            }
        }else {
            if (queue == null) {
                queue = Volley.newRequestQueue(view.getContext());
            }

            String URL = ipAdress + ":8080/monitoring-system/service/user/login?email=" + userEmail + "&password=" + userPassword;

            JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.GET, URL, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            try {
                                String responseStatus = jsonObject.getString("status");
                                String responseMessage = jsonObject.getString("message");

                                if(responseStatus.equals("success")){
                                    userSession.setLoggedIn(true, userEmail);
                                    view.successAuthentication(responseMessage);
                                }else{
                                    view.errorAuthentication(responseMessage);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Log.e("Login Error:", volleyError.toString());
                        }
                    });
            queue.add(loginRequest);
        }

    }
}
