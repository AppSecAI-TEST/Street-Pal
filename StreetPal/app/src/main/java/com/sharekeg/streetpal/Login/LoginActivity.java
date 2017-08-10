package com.sharekeg.streetpal.Login;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sharekeg.streetpal.Androidversionapi.ApiInterface;
import com.sharekeg.streetpal.Home.HomeActivity;
import com.sharekeg.streetpal.R;
import com.sharekeg.streetpal.Registration.SelectTrustedContactsActivity;
import com.sharekeg.streetpal.Registration.SignUpActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private Button btLogin;
    private TextView tvSignUp;
    private String userName;
    private String password;
    private String token;
    private ProgressDialog pDialog;
    View focusView = null;


    @Override
    public void onBackPressed() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(R.string.exit);
        dialog.setMessage(R.string.txt_are_you_sure);
        dialog.setPositiveButton(android.R.string.no, null);
        dialog.setNegativeButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                LoginActivity.super.onBackPressed();
            }
        });
        dialog.show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUsername = (EditText) findViewById(R.id.etusername);
        etPassword = (EditText) findViewById(R.id.etpassword);
        btLogin = (Button) findViewById(R.id.btnLogin);
        tvSignUp = (TextView) findViewById(R.id.link_to_register);
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(i);
            }
        });
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });


    }

    private void attemptLogin() {

        boolean mCancel = this.loginValidation();
        if (mCancel) {
            focusView.requestFocus();
        } else {
            loginProcessWithRetrofit(userName, password);
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage(String.valueOf(R.string.dialog_logging));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }


    }

    private boolean loginValidation() {
        userName = etUsername.getText().toString();
        password = etPassword.getText().toString();
        boolean cancel = false;

        if (TextUtils.isEmpty(userName)) {
            etUsername.setError(getText(R.string.loginmail_validation));
            focusView = etUsername;
            cancel = true;

        }
        if (TextUtils.isEmpty(password)) {
            etPassword.setError(getText(R.string.loginpass_validation));
            focusView = etPassword;
            cancel = true;

        }
        return cancel;
    }


    private void loginProcessWithRetrofit(String userName, String password) {

        ApiInterface mApiService = this.getInterfaceService();
        Call<LoginCredentials> mService = mApiService.loginWithCredentials(new LoginCredentials(userName, password));
        mService.enqueue(new Callback<LoginCredentials>() {
            @Override

            public void onResponse(Call<LoginCredentials> call, Response<LoginCredentials> response) {
                token = response.body().getToken();
                Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                i.putExtra("Token", token);
                startActivity(i);
                pDialog.dismiss();
                finish();


            }

            @Override
            public void onFailure(Call<LoginCredentials> call, Throwable t) {
                pDialog.dismiss();
                Snackbar.make(btLogin, R.string.txt_connection_Failed, Snackbar.LENGTH_INDEFINITE).setAction(R.string.txt_try_toUpload_again, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        attemptLogin();
                        pDialog.show();
                    }
                }).show();
            }
        });

    }


    private ApiInterface getInterfaceService() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://sharekeg.com:8088/v0/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final ApiInterface mInterfaceService = retrofit.create(ApiInterface.class);
        return mInterfaceService;
    }

}



