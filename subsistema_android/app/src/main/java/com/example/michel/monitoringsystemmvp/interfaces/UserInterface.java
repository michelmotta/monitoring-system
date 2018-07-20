package com.example.michel.monitoringsystemmvp.interfaces;

import android.content.Context;

import org.json.JSONObject;

public interface UserInterface {

    interface View{

        Context getContext();

        void setUserinfos(String userId, String userUsername, String userPassword, String userEmail, String userPhone);

        void userEditFeedbackMessageSuccess(String responseMessage);

        void userEditFeedbackMessageError(String responseMessage);
    }

    interface Presenter{
        void requestApiUserInfo();

        void requestApiEditUserAccountInfo(String userId, String userUsername, String userPassword, String userEmail, String userPhone);
    }

    interface Model{}
}
