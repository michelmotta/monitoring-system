package com.example.michel.monitoringsystemmvp.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class UserSession {

    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Context ctx;

    public UserSession(Context ctx) {
        this.ctx = ctx;
        prefs = ctx.getSharedPreferences("monitorsystemmvp", Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void setLoggedIn(boolean loggedin, String email){
        editor.putBoolean("loggedInmode", loggedin);
        editor.putString("userEmail", email);
        editor.commit();
    }

    public String getSessionEmail() {
        String email = prefs.getString("userEmail","");
        return email;
    }

    public boolean loggedIn(){
        return prefs.getBoolean("loggedInmode", false);
    }
}
