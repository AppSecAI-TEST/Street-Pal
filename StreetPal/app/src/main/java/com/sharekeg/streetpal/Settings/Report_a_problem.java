package com.sharekeg.streetpal.Settings;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sharekeg.streetpal.R;

public class Report_a_problem extends AppCompatActivity {
    private TextView cancel;
    private Button send;
    private String feedback;
    EditText edittext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_a_problem);
        cancel = (TextView) findViewById(R.id.cancel);
        edittext = (EditText) findViewById(R.id.edittext);
        send = (Button) findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedback = edittext.getText().toString();
                if (!feedback.matches("")) {
                    send.setVisibility(View.VISIBLE);
                    Intent email = new Intent(android.content.Intent.ACTION_SEND);

                    email.setType("plain/text");
                    email.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"reemnabil570@gmail.com"});
                    email.putExtra(android.content.Intent.EXTRA_SUBJECT, "Lads to Leaders/Leaderettes Questions and/or Comments");
                    email.putExtra(android.content.Intent.EXTRA_TEXT,"Feedback :" + edittext.getText().toString() );

                   /* Send it off to the Activity-Chooser */
                    startActivity(Intent.createChooser(email, getApplicationContext().getResources()
                            .getString(R.string.sendmail_feedbackActivity)));
                    return;

                } else {
                   send.setVisibility(View.GONE);
                    Toast.makeText(Report_a_problem.this, R.string.feedback_err, Toast.LENGTH_SHORT).show();

                }



            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(Report_a_problem.this, SettingsActivity.class);
                startActivity(i);
            }
        });


    }
}
