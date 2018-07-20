package com.example.michel.monitoringsystemmvp.view;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;


import com.example.michel.monitoringsystemmvp.R;
import com.example.michel.monitoringsystemmvp.utils.UserSession;
import com.example.michel.monitoringsystemmvp.interfaces.MainInterface;
import com.example.michel.monitoringsystemmvp.presenter.MainPresenter;
import com.onesignal.OneSignal;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements MainInterface.View{

    @BindView(R.id.mainToolbar)
    Toolbar toolbar;

    @BindView(R.id.textViewAverageTemp)
    TextView textViewAverageTemp;

    @BindView(R.id.textViewAverageHum)
    TextView textViewAverageHum;

    private MainInterface.Presenter mainPresenter;
    private UserSession userSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // OneSignal Initialization
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        ButterKnife.bind(this);

        toolbar.setTitle(this.getResources().getString(R.string.appPageTitleMain));

        setSupportActionBar(toolbar);

        mainPresenter = new MainPresenter(this);
        userSession = new UserSession(this);

        if(userSession.loggedIn()){

            final Handler handler = new Handler();
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        public void run() {
                            mainPresenter.requestApiTemperatureStatus();
                        }
                    });
                }
            };
            timer.schedule(task, 1000, 30000);

        }else{
            logout();
        }

    }

    private void logout(){
        mainPresenter.logoutUser();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void setAverageTemperatureHumidity(String averageTemperature, String averageHumidity) {
        textViewAverageTemp.setText(averageTemperature + "ºC");
        textViewAverageHum.setText(averageHumidity + "%");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        if(item.getItemId() == R.id.action_historic)
            startActivity(new Intent(MainActivity.this, HistoricActivity.class));

        if(item.getItemId() == R.id.action_graph)
            startActivity(new Intent(MainActivity.this, GraphActivity.class));

        if(item.getItemId() == R.id.action_config)
            startActivity(new Intent(MainActivity.this, ConfigActivity.class));

        if(item.getItemId() == R.id.action_user)
            startActivity(new Intent(MainActivity.this, UserActivity.class));

        if(item.getItemId() == R.id.action_logout)
            logout();

        return true;
    }
}
