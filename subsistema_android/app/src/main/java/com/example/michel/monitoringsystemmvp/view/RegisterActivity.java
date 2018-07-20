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
import android.widget.TextView;
import android.widget.Toast;

import com.example.michel.monitoringsystemmvp.R;
import com.example.michel.monitoringsystemmvp.interfaces.RegisterInterface;
import com.example.michel.monitoringsystemmvp.presenter.RegisterPresenter;
import com.example.michel.monitoringsystemmvp.utils.BrPhoneNumberFormatter;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity implements RegisterInterface.View{

    @BindView(R.id.editTextRegisterUsername)
    EditText editTextRegisterUsername;

    @BindView(R.id.editTextRegisterUsername_layout)
    TextInputLayout editTextRegisterUsername_layout;

    @BindView(R.id.editTextRegisterEmail)
    EditText editTextRegisterEmail;

    @BindView(R.id.editTextRegisterEmail_layout)
    TextInputLayout editTextRegisterEmail_layout;

    @BindView(R.id.editTextRegisterPassword)
    EditText editTextRegisterPassword;

    @BindView(R.id.editTextRegisterPassword_layout)
    TextInputLayout editTextRegisterPassword_layout;

    @BindView(R.id.editTextRegisterRetypePassword)
    EditText editTextRegisterRetypePassword;

    @BindView(R.id.editTextRegisterRetypePassword_layout)
    TextInputLayout editTextRegisterRetypePassword_layout;

    @BindView(R.id.editTextRegisterPhone)
    EditText editTextRegisterPhone;

    @BindView(R.id.editTextRegisterPhone_layout)
    TextInputLayout editTextRegisterPhone_layout;

    private ProgressDialog progressDialog;
    private RegisterInterface.Presenter registerPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);
        registerPresenter = new RegisterPresenter(this);

        BrPhoneNumberFormatter addLineNumberFormatter = new BrPhoneNumberFormatter(new WeakReference<EditText>(editTextRegisterPhone));
        editTextRegisterPhone.addTextChangedListener(addLineNumberFormatter);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void passwordConfirmationError() {
        editTextRegisterPassword_layout.setErrorEnabled(true);
        editTextRegisterPassword_layout.setError(this.getResources().getString(R.string.registerDoNotMatch));

        editTextRegisterRetypePassword_layout.setErrorEnabled(true);
        editTextRegisterRetypePassword_layout.setError(this.getResources().getString(R.string.registerDoNotMatch));
        editTextRegisterRetypePassword.setText("");
        progressDialog.dismiss();
    }

    @Override
    public void requiredFieldsError() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_error_layout, (ViewGroup) findViewById(R.id.toastErrorWrap));

        TextView toastText = layout.findViewById(R.id.toastErrorText);
        ImageView toastImage = layout.findViewById(R.id.toastErrorImage);

        toastText.setText(this.getResources().getString(R.string.registerRequiredField));
        toastImage.setImageResource(R.drawable.ic_toast_error);

        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
        progressDialog.dismiss();
    }

    @Override
    public void accountCreationSuccess(String message) {
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

        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        finish();
        progressDialog.dismiss();
    }

    @Override
    public void accountCreationError(String message) {
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


    @OnClick(R.id.buttonCancelRegister)
    void onClickButtonCancelRegister(View view){
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        finish();
    }

    @OnClick(R.id.buttonCreateAccount)
    void onClickButtonCreateAccount(View view){
        String registerUsername = editTextRegisterUsername.getText().toString();
        String registerEmail = editTextRegisterEmail.getText().toString();
        String registerPassword = editTextRegisterPassword.getText().toString();
        String registerPasswordRetype = editTextRegisterRetypePassword.getText().toString();
        String registerPhone = editTextRegisterPhone.getText().toString();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(this.getResources().getString(R.string.appLoading));
        progressDialog.show();

        registerPresenter.validationRegister(registerUsername, registerEmail, registerPassword, registerPasswordRetype, registerPhone);

    }

}
