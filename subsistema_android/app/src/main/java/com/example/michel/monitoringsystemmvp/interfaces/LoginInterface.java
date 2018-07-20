package com.example.michel.monitoringsystemmvp.interfaces;

import android.content.Context;

import org.json.JSONObject;

public interface LoginInterface {

    interface View{

        Context getContext();

        void userEmailEmptyError();

        void userPasswordEmptyError();

        void successAuthentication(String message);

        void errorAuthentication(String message);


    }

    interface Presenter{
        void loginValidation(String userEmail, String userPassword);
    }

    interface Model{}
}
