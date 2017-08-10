package com.sharekeg.streetpal.Settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sharekeg.streetpal.Home.EditProfileActivity;
import com.sharekeg.streetpal.Home.HomeActivity;
import com.sharekeg.streetpal.Login.LoginActivity;
import com.sharekeg.streetpal.R;
import com.sharekeg.streetpal.Registration.ConfirmationActivity;
import com.sharekeg.streetpal.Registration.SignUpActivity;

public class SettingsActivity extends AppCompatActivity {
    private ImageView changepassword , languageimg , IV_editProfile ,IV_trusted_contact ,IV_privacy_policy ,IV_Report_a_problem,btnBack;
    private TextView logout ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        logout =(TextView)findViewById(R.id.logout) ;
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences SM = getSharedPreferences("userrecord", 0);
                SharedPreferences.Editor edit = SM.edit();
                edit.putBoolean("userlogin", false);
                edit.commit();
                Intent intent = new Intent(SettingsActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        IV_trusted_contact =(ImageView)findViewById(R.id.IV_trusted_contact) ;
        IV_trusted_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(SettingsActivity.this, EditSelectedTrustedContactsActivity.class);
                startActivity(i);

            }
        });

        IV_Report_a_problem =(ImageView)findViewById(R.id.IV_Report_a_problem) ;
        IV_Report_a_problem .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(SettingsActivity.this,Report_a_problem.class);
                startActivity(i);

            }
        });

        IV_privacy_policy    =(ImageView)findViewById(R.id.IV_privacy_policy) ;
        IV_privacy_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(SettingsActivity.this, PrivacyPolicyActivity.class);
                startActivity(i);

            }
        });
        IV_editProfile=(ImageView)findViewById(R.id.IV_editProfile) ;
        IV_editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(SettingsActivity.this, EditProfileActivity.class);
                startActivity(i);

            }
        });
        changepassword = (ImageView) findViewById(R.id.changepasswordimg);
        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(SettingsActivity.this, ChangePasswordActivity.class);
                startActivity(i);

            }
        });


        languageimg = (ImageView) findViewById(R.id.languageimg);
        languageimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(SettingsActivity.this, LanguageActivity.class);
                startActivity(i);

            }
        });

        btnBack=(ImageView)findViewById(R.id.ivSettingsBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
            }
        });


    }
}
