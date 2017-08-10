package com.sharekeg.streetpal.Settings;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sharekeg.streetpal.Androidversionapi.ApiInterface;
import com.sharekeg.streetpal.Home.HomeActivity;
import com.sharekeg.streetpal.Login.LoginActivity;
import com.sharekeg.streetpal.Login.LoginCredentials;
import com.sharekeg.streetpal.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChangePasswordActivity extends AppCompatActivity {
    private TextView cancel_action;
    private EditText newpassword, confirmpassword, oldpassword;
    String textnewpassword, textconfirmpassword;
    TextView btndone;
    boolean valid;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        btndone = (TextView) findViewById(R.id.btndone);
        oldpassword = (EditText) findViewById(R.id.oldpassword);
        newpassword = (EditText) findViewById(R.id.newpassword);
        confirmpassword = (EditText) findViewById(R.id.confirmpassword);
        btndone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptChangePassword();
                textnewpassword = newpassword.getText().toString();
                textconfirmpassword = confirmpassword.getText().toString();
                if (textnewpassword.equals(textconfirmpassword)&&valid) {
                    Intent i = new Intent(ChangePasswordActivity.this, SettingsActivity.class);
                    startActivity(i);

                }
                if (!(textnewpassword.equals(textconfirmpassword))) {
                    Toast.makeText(getApplicationContext(),
                            R.string.err_changePass, Toast.LENGTH_SHORT).show();
                }
                validateForm();
            }
        });
        cancel_action = (TextView) findViewById(R.id.cancel_action);
        cancel_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChangePasswordActivity.this, SettingsActivity.class);
                startActivity(i);

            }
        });


    }

    String oldUserPassword;

    private boolean validateForm() {
        valid = true;

        oldUserPassword = oldpassword.getText().toString();
        if (TextUtils.isEmpty(oldUserPassword) || oldUserPassword.length() < 6) {
            oldpassword.setError(getApplicationContext().getResources().getString(R.string.loginpass_validation_invalid));
            valid = false;
        } else {
            oldpassword.setError(null);
        }
        textnewpassword = newpassword.getText().toString();
        if (TextUtils.isEmpty(textnewpassword) || textnewpassword.length() < 6) {
            newpassword.setError(getApplicationContext().getResources().getString(R.string.loginpass_validation_invalid));
            valid = false;
        } else {
            newpassword.setError(null);
        }
        textconfirmpassword = confirmpassword.getText().toString();
        if (TextUtils.isEmpty(textconfirmpassword) || textconfirmpassword.length() < 6) {
            confirmpassword.setError(getApplicationContext().getResources().getString(R.string.loginpass_validation_invalid));
            valid = false;
        } else {
            confirmpassword.setError(null);
        }

        return valid;
    }

    private ProgressDialog pDialog;
    View focusView = null;

    private void attemptChangePassword() {

        boolean mCancel = this.validateForm();
        if (mCancel) {
            focusView.requestFocus();
        } else {
            SendPasswordWithRetrofit(oldUserPassword, textnewpassword, textconfirmpassword);
            pDialog = new ProgressDialog(ChangePasswordActivity.this);
            pDialog.setMessage(getApplicationContext().getResources().getString(R.string.changepass));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
    }

    private ApiInterface getInterfaceService() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://sharekeg.com:8088")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final ApiInterface mInterfaceService = retrofit.create(ApiInterface.class);
        return mInterfaceService;
    }

    private void SendPasswordWithRetrofit(String oldUserPassword, String textnewpassword, String textconfirmpassword) {

        ApiInterface mApiService = this.getInterfaceService();
        Call<ChangePassword> Service = mApiService.sendPassword(new ChangePassword(oldUserPassword, textnewpassword, textconfirmpassword));
        Service.enqueue(new Callback<ChangePassword>() {
            @Override
            public void onResponse(Call<ChangePassword> call, Response<ChangePassword> response) {
                token = response.body().getToken();
                Intent i = new Intent(ChangePasswordActivity.this, SettingsActivity.class);
                i.putExtra("Token", token);
                startActivity(i);
                pDialog.dismiss();
                finish();
            }

            @Override
            public void onFailure(Call<ChangePassword> call, Throwable throwable) {
                pDialog.dismiss();
                Snackbar.make(btndone, R.string.txt_connection_Failed, Snackbar.LENGTH_INDEFINITE).setAction
                        (R.string.txt_try_toUpload_again, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                attemptChangePassword();
                                pDialog.show();
                            }
                        }).show();

            }
        });


    }

}
