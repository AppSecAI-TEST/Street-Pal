package com.sharekeg.streetpal.Settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sharekeg.streetpal.R;

import java.text.Bidi;
import java.util.Locale;

public class LanguageActivity extends AppCompatActivity {
    private TextView english, arabic;
    SharedPreferences languagepref;
    ImageView english1, arabic1;
    TextView back_action;
    int lang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        english1 = (ImageView) findViewById(R.id.english1);
        arabic1 = (ImageView) findViewById(R.id.arabic1);
        back_action = (TextView) findViewById(R.id.cancel_action);
        english = (TextView) findViewById(R.id.english);
        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                english1.setBackgroundResource(R.drawable.ic_selected);
                english1.setVisibility(View.VISIBLE);
                arabic1.setVisibility(View.INVISIBLE);
                lang=1;
            }
        });
        arabic = (TextView) findViewById(R.id.arabic);
        arabic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arabic1.setBackgroundResource(R.drawable.ic_selected);
                english1.setVisibility(View.INVISIBLE);
                arabic1.setVisibility(View.VISIBLE);
                lang=2;
            }
        });


        back_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (lang==1) {
                    Toast.makeText(getApplicationContext(),
                            "Done!", Toast.LENGTH_SHORT).show();
                    setLTR();

                    Intent i = new Intent(LanguageActivity.this, SettingsActivity.class);
                    startActivity(i);


                }
                if (lang==2) {
                    Toast.makeText(getApplicationContext(),
                            "Done!", Toast.LENGTH_SHORT).show();

                    setRTL();
                    Intent i = new Intent(LanguageActivity.this, SettingsActivity.class);
                    startActivity(i);

                }
            }
        });


    }
    public void setRTL(){
        String languageToLoad  = "ar"; // rtl language Arabic
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);

        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        //layout direction
        Bidi b = new Bidi(languageToLoad, Bidi.DIRECTION_DEFAULT_RIGHT_TO_LEFT);
        b.isRightToLeft();
        //save current locale in SharedPreferences
        languagepref = getSharedPreferences("language",MODE_PRIVATE);
        SharedPreferences.Editor editor = languagepref.edit();
        editor.putString("languageToLoad",languageToLoad );
        editor.commit();
//        ReplaceFontForArabic.replaceArabicFont(ChooseLanguage.this,"DEFAULT","cocon-858df15efcbd1077e4131a6b1673af62.ttf");
    }
    public void setLTR(){
        String languageToLoad  = "en"; // ltr language English
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);

        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        //layout direction
        Bidi b = new Bidi(languageToLoad, Bidi.DIRECTION_DEFAULT_LEFT_TO_RIGHT);
        b.isLeftToRight();
        //save current locale in SharedPreferences
        languagepref = getSharedPreferences("language",MODE_PRIVATE);
        SharedPreferences.Editor editor = languagepref.edit();
        editor.putString("languageToLoad",languageToLoad );
        editor.commit();
//        ReplceFontForEnglish.replceEnglishFont(ChooseLanguage.this,"DEFAULT","Roboto-Regular.ttf");
    }

}
