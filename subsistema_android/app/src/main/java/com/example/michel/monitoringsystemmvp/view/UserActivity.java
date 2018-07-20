package com.example.michel.monitoringsystemmvp.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.michel.monitoringsystemmvp.R;
import com.example.michel.monitoringsystemmvp.interfaces.UserInterface;
import com.example.michel.monitoringsystemmvp.presenter.UserPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserActivity extends AppCompatActivity implements UserInterface.View{

    @BindView(R.id.mainToolbar)
    Toolbar mainToolbar;

    @BindView(R.id.editTextUserId)
    EditText editTextUserId;

    @BindView(R.id.editTextUserId_layout)
    TextInputLayout editTextUserId_layout;

    @BindView(R.id.editTextUsername)
    EditText editTextUsername;

    @BindView(R.id.editTextUsername_layout)
    TextInputLayout editTextUsername_layout;

    @BindView(R.id.editTextUserEmail)
    EditText editTextUserEmail;

    @BindView(R.id.editTextUserEmail_layout)
    TextInputLayout editTextUserEmail_layout;

    @BindView(R.id.editTextUserPassword)
    EditText editTextUserPassword;

    @BindView(R.id.editTextUserPassword_layout)
    TextInputLayout editTextUserPassword_layout;

    @BindView(R.id.editTextUserPhone)
    EditText editTextUserPhone;

    @BindView(R.id.editTextUserPhone_layout)
    TextInputLayout editTextUserPhone_layout;

    private ProgressDialog progressDialog;
    private UserInterface.Presenter userPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        ButterKnife.bind(this);

        mainToolbar.setTitle(this.getResources().getString(R.string.appPageTitleUser));
        setSupportActionBar(mainToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userPresenter = new UserPresenter(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(this.getResources().getString(R.string.appLoading));
        progressDialog.show();

        userPresenter.requestApiUserInfo();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_second, menu);
        return true;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void setUserinfos(String userId, String userUsername, String userPassword, String userEmail, String userPhone) {
        editTextUserId.setText(userId);
        editTextUserId.setEnabled(false);
        editTextUsername.setText(userUsername);
        editTextUserPassword.setText(userPassword);
        editTextUserEmail.setText(userEmail);
        editTextUserPhone.setText(userPhone);

        progressDialog.dismiss();
    }

    @Override
    public void userEditFeedbackMessageSuccess(String responseMessage) {
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
    }

    @Override
    public void userEditFeedbackMessageError(String responseMessage) {
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
    }

    @OnClick(R.id.buttonUserEditAccountInfo)
    void onClickEditUserAccountInfo(View view){
        String userId = editTextUserId.getText().toString();
        String userUsername = editTextUsername.getText().toString();
        String userPassword = editTextUserPassword.getText().toString();
        String userEmail = editTextUserEmail.getText().toString();
        String userPhone = editTextUserPhone.getText().toString();

        userPresenter.requestApiEditUserAccountInfo(userId, userUsername, userPassword, userEmail, userPhone);
    }
}
