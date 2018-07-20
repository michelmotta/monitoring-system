package com.example.michel.monitoringsystemmvp.presenter;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.michel.monitoringsystemmvp.interfaces.RegisterInterface;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterPresenter implements RegisterInterface.Presenter{

    private RegisterInterface.View view;
    private RequestQueue queue;
    //private String ipAdress = "http://10.0.2.2";
    //private String ipAdress = "http://10.87.7.210";
    private String ipAdress = "http://10.87.9.196";

    public RegisterPresenter(RegisterInterface.View view) {
        this.view = view;
    }

    @Override
    public void validationRegister(String registerUsername, String registerEmail, String registerPassword, String registerPasswordRetype, String registerPhone) {

        if(registerUsername.isEmpty() || registerEmail.isEmpty() || registerPassword.isEmpty() || registerPasswordRetype.isEmpty() || registerPhone.isEmpty()){
            view.requiredFieldsError();
        }else{
            if(registerPassword.equals(registerPasswordRetype)){
                createApiUserAccount(registerUsername, registerEmail, registerPassword, registerPhone);
            }
            else{
                view.passwordConfirmationError();
            }
        }
    }

    public void createApiUserAccount(String registerUsername, String registerEmail, String registerPassword, String registerPhone){


        String URL = ipAdress + ":8080/monitoring-system/service/user/add";
        if (queue == null) {
            queue = Volley.newRequestQueue(view.getContext());
        }

        JSONObject jsonAccount = new JSONObject();
        try{
            jsonAccount.put("username", registerUsername);
            jsonAccount.put("email", registerEmail);
            jsonAccount.put("password", registerPassword);
            jsonAccount.put("phone", registerPhone);

        }catch (JSONException e){
            e.printStackTrace();
        }

        JsonObjectRequest userAccountCreate = new JsonObjectRequest(Request.Method.POST, URL, jsonAccount,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            String responseStatus = jsonObject.getString("status");
                            String responseMessage = jsonObject.getString("message");

                            if(responseStatus.equals("success")){
                                view.accountCreationSuccess(responseMessage);
                            }else{
                                view.accountCreationError(responseMessage);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e("Register Error:", volleyError.toString());
                    }
                });
        queue.add(userAccountCreate);
    }
}
