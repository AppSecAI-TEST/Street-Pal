package com.sharekeg.streetpal.Settings;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sharekeg.streetpal.R;
import com.sharekeg.streetpal.Registration.SelectTrustedContactsActivity;
import com.sharekeg.streetpal.Registration.SelectTrustedContactsActivity2;

public class EditSelectedTrustedContactsActivity extends AppCompatActivity {
ImageView IV_add_new ;
    private TextView tvName, tvNumber ,cancel;
    private String Name = null;
    private String Number = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_selected_trusted_contacts);
//        Number=getIntent().getExtras().getString("contactNumber");
     //   Name = getIntent().getExtras().getString("contactName");
        cancel =(TextView)findViewById(R.id.cancel) ;
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EditSelectedTrustedContactsActivity.this, SettingsActivity.class);
                startActivity(i);
            }
        });
        tvName = (TextView) findViewById(R.id.tvName);
      //  tvName.setText(Name);
        tvNumber = (TextView) findViewById(R.id.tvNumber);
      //  tvNumber.setText(Number);

        IV_add_new =(ImageView) findViewById(R.id.IV_add_new) ;
        IV_add_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EditSelectedTrustedContactsActivity.this, SelectTrustedContactsActivity.class);
                startActivity(i);

            }
        });

    }
}
