package com.example.michel.monitoringsystemmvp.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.michel.monitoringsystemmvp.R;
import com.example.michel.monitoringsystemmvp.interfaces.ConfigInterface;
import com.example.michel.monitoringsystemmvp.presenter.ConfigPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConfigActivity extends AppCompatActivity implements ConfigInterface.View{

    @BindView(R.id.mainToolbar)
    Toolbar mainToolbar;

    @BindView(R.id.editTextSettingMaxTempLimit)
    EditText editTextSettingMaxTempLimit;

    @BindView(R.id.editTextSettingMaxTempLimit_layout)
    TextInputLayout editTextSettingMaxTempLimit_layout;

    @BindView(R.id.editTextSettingMinTempLimit)
    EditText editTextSettingMinTempLimit;

    @BindView(R.id.editTextSettingMinTempLimit_layout)
    TextInputLayout editTextSettingMinTempLimit_layout;

    @BindView(R.id.editTextSettingMaxHumLimit)
    EditText editTextSettingMaxHumLimit;

    @BindView(R.id.editTextSettingMaxHumLimit_layout)
    TextInputLayout editTextSettingMaxHumLimit_layout;

    @BindView(R.id.editTextSettingMinHumLimit)
    EditText editTextSettingMinHumLimit;

    @BindView(R.id.editTextSettingMinHumLimit_layout)
    TextInputLayout editTextSettingMinHumLimit_layout;

    @BindView(R.id.editTextSettingReadingRange)
    EditText editTextSettingReadingRange;

    @BindView(R.id.editTextSettingReadingRange_layout)
    TextInputLayout editTextSettingReadingRange_layout;

    private ProgressDialog progressDialog;
    private ConfigInterface.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        ButterKnife.bind(this);

        mainToolbar.setTitle(this.getResources().getString(R.string.appPageTitleConfig));

        setSupportActionBar(mainToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        presenter = new ConfigPresenter(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(this.getResources().getString(R.string.appLoading));
        progressDialog.show();

        presenter.requestApiConfigData();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void setConfigData(String configMaxTempLim, String configMinTempLim, String configMaxHumLim, String configMinHumLim, String configReadingRange) {
        editTextSettingMaxTempLimit.setText(configMaxTempLim);
        editTextSettingMinTempLimit.setText(configMinTempLim);
        editTextSettingMaxHumLimit.setText(configMaxHumLim);
        editTextSettingMinHumLimit.setText(configMinHumLim);
        editTextSettingReadingRange.setText(configReadingRange);

        progressDialog.dismiss();
    }

    @Override
    public void settingsEditFeedbackMessageSuccess(String responseMessage) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_success_layout, (ViewGroup) findViewById(R.id.toastSuccessWrap));

        TextView toastText = layout.findViewById(R.id.toastSuccessText);
        ImageView toastImage = layout.findViewById(R.id.toastSuccessImage);

        toastText.setText(responseMessage);
        toastImage.setImageResource(R.drawable.ic_toast_success);

        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
        finish();
        progressDialog.dismiss();
    }

    @Override
    public void settingsEditFeedbackMessageError(String responseMessage) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_error_layout, (ViewGroup) findViewById(R.id.toastErrorWrap));


        TextView toastText = layout.findViewById(R.id.toastErrorText);
        ImageView toastImage = layout.findViewById(R.id.toastErrorImage);

        toastText.setText(responseMessage);
        toastImage.setImageResource(R.drawable.ic_toast_error);

        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
        progressDialog.dismiss();
    }

    @OnClick(R.id.buttonSettingEdit)
    void onClickEditSystemSettings(View view){
        String configMaxTempLim = editTextSettingMaxTempLimit.getText().toString();
        String configMinTempLim =  editTextSettingMinTempLimit.getText().toString();
        String configMaxHumLim = editTextSettingMaxHumLimit.getText().toString();
        String configMinHumLim = editTextSettingMinHumLimit.getText().toString();
        String configReadingRange = editTextSettingReadingRange.getText().toString();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(this.getResources().getString(R.string.appLoading));
        progressDialog.show();

        presenter.editApiSystemSettings(configMaxTempLim, configMinTempLim, configMaxHumLim, configMinHumLim, configReadingRange);
    }
}
