package com.example.michel.monitoringsystemmvp.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.michel.monitoringsystemmvp.R;
import com.example.michel.monitoringsystemmvp.presenter.LoginPresenter;
import com.example.michel.monitoringsystemmvp.interfaces.LoginInterface;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginInterface.View{

    @BindView(R.id.editTextEmail)
    EditText editTextEmail;

    @BindView(R.id.editTextEmail_layout)
    TextInputLayout editTextEmail_layout;

    @BindView(R.id.editTextPassword)
    EditText editTextPassword;

    @BindView(R.id.editTextPassword_layout)
    TextInputLayout editTextPassword_layout;

    private ProgressDialog progressDialog;
    private LoginInterface.Presenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
        loginPresenter = new LoginPresenter(this);

    }

    @Override
    public Context getContext() {
        return this;
    }


    @OnClick(R.id.buttonRegister)
    void onClickButtonRegister(View view){
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        finish();
    }

    @OnClick(R.id.buttonLogin)
    void onClickLogin(View view){
        String userEmail = editTextEmail.getText().toString();
        String userPassword = editTextPassword.getText().toString();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(this.getResources().getString(R.string.appLoading));
        progressDialog.show();

        loginPresenter.loginValidation(userEmail, userPassword);
    }

    @Override
    public void userEmailEmptyError() {
        editTextEmail_layout.setErrorEnabled(true);
        editTextEmail_layout.setError(this.getResources().getString(R.string.loginTextEmptyField));

        progressDialog.dismiss();
    }

    @Override
    public void userPasswordEmptyError() {
        editTextPassword_layout.setErrorEnabled(true);
        editTextPassword_layout.setError(this.getResources().getString(R.string.loginTextEmptyField));

        progressDialog.dismiss();
    }

    @Override
    public void successAuthentication(String message) {
        startActivity(new Intent(this, MainActivity.class));

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_success_layout, (ViewGroup) findViewById(R.id.toastSuccessWrap));

        TextView toastText = layout.findViewById(R.id.toastSuccessText);
        ImageView toastImage = layout.findViewById(R.id.toastSuccessImage);

        toastText.setText(message);
        toastImage.setImageResource(R.drawable.ic_toast_success);

        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
        progressDialog.dismiss();
    }

    @Override
    public void errorAuthentication(String message) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_error_layout, (ViewGroup) findViewById(R.id.toastErrorWrap));

        TextView toastText = layout.findViewById(R.id.toastErrorText);
        ImageView toastImage = layout.findViewById(R.id.toastErrorImage);

        toastText.setText(message);
        toastImage.setImageResource(R.drawable.ic_toast_error);

        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
        progressDialog.dismiss();
    }
}
