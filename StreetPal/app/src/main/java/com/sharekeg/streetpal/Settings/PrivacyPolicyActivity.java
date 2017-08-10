package com.sharekeg.streetpal.Settings;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.sharekeg.streetpal.R;

public class PrivacyPolicyActivity extends AppCompatActivity {
 private TextView cancel_action ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        cancel_action = (TextView)findViewById(R.id.cancel_action);
        cancel_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PrivacyPolicyActivity.this, SettingsActivity.class);
                startActivity(i);
            }
        });
    }
}
