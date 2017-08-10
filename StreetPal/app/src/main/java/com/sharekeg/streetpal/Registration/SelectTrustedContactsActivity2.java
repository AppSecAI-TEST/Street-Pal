package com.sharekeg.streetpal.Registration;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sharekeg.streetpal.R;

public class SelectTrustedContactsActivity2 extends AppCompatActivity {
    private TextView tvSkip;
    private ImageView ivBack, ivNext;
    private TextView tvName, tvNumber;
    private String contactName = null;
    private String contactNumber = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_trusted_contacts2);
        contactNumber=getIntent().getExtras().getString("contactNumber");
        contactName = getIntent().getExtras().getString("contactName");

        tvName = (TextView) findViewById(R.id.tvName);
        tvName.setText(contactName);
        tvNumber = (TextView) findViewById(R.id.tvNumber);
        tvNumber.setText(contactNumber);

        ivBack = (ImageView) findViewById(R.id.ivback);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SelectTrustedContactsActivity2.this, SelectTrustedContactsActivity.class);
                startActivity(i);
            }
        });


        ivNext = (ImageView) findViewById(R.id.ivNext);
        ivNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(SelectTrustedContactsActivity2.this)
                        .setTitle(R.string.select_trusted_contact_dialog_title)
                        .setMessage(R.string.select_trusted_contact_dialog_message)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(SelectTrustedContactsActivity2.this, CongratulationActivity.class);
                                startActivity(i);
                                finish();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();

            }
        });

        tvSkip = (TextView) findViewById(R.id.tvSkip);
        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(SelectTrustedContactsActivity2.this)
                        .setTitle(R.string.select_trusted_contact_dialog_title)
                        .setMessage(R.string.select_trusted_contact_dialog_message_skipping)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(SelectTrustedContactsActivity2.this, CongratulationActivity.class);
                                startActivity(i);
                                finish();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();
            }
        });

    }
}
