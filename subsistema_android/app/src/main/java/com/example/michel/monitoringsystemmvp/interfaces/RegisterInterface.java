package com.example.michel.monitoringsystemmvp.interfaces;

import android.content.Context;

public interface RegisterInterface {

    interface View{
        Context getContext();

        void passwordConfirmationError();

        void requiredFieldsError();

        void accountCreationSuccess(String message);

        void accountCreationError(String message);
    }

    interface Presenter{

        void validationRegister(String registerUsername, String registerEmail, String registerPassword, String registerPasswordRetype, String registerPhone);
    }

    interface Model{

    }
}
