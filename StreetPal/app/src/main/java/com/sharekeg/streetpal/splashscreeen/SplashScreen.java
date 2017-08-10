package com.sharekeg.streetpal.splashscreeen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.sharekeg.streetpal.Language.ChooseLanguage;
import com.sharekeg.streetpal.Login.LoginActivity;
import com.sharekeg.streetpal.R;

import org.w3c.dom.Text;

import java.text.Bidi;
import java.util.Locale;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());

        int secondsDelayed = 1;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                SharedPreferences languagepref = getSharedPreferences("language", MODE_PRIVATE);
                String language = languagepref.getString("languageToLoad", "");
                switch (language) {
                    case "ar":
                        Locale localeAR = new Locale(language);
                        Locale.setDefault(localeAR);

                        Configuration configAR = new Configuration();
                        configAR.locale = localeAR;
                        getBaseContext().getResources().updateConfiguration(configAR,
                                getBaseContext().getResources().getDisplayMetrics());
                        //layout direction
                        Bidi bAR = new Bidi(language, Bidi.DIRECTION_DEFAULT_RIGHT_TO_LEFT);
                        bAR.isRightToLeft();
//                        ReplaceFontForArabic.replaceArabicFont(SplashScreen.this,"DEFAULT","cocon-858df15efcbd1077e4131a6b1673af62.ttf");
                        break;
                    case "en":
                        Locale localeEN = new Locale(language);
                        Locale.setDefault(localeEN);

                        Configuration configEN = new Configuration();
                        configEN.locale = localeEN;
                        getBaseContext().getResources().updateConfiguration(configEN,
                                getBaseContext().getResources().getDisplayMetrics());
                        //layout direction
                        Bidi bEN = new Bidi(language, Bidi.DIRECTION_DEFAULT_LEFT_TO_RIGHT);
                        bEN.isLeftToRight();
//                        ReplceFontForEnglish.replceEnglishFont(SplashScreen.this,"DEFAULT","Roboto-Regular.ttf");
                }


                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                boolean previouslyStarted = prefs.getBoolean("previously_started", false);
                if (!previouslyStarted)

                {
                    SharedPreferences.Editor edit = prefs.edit();
                    edit.putBoolean("previously_started", Boolean.TRUE);
                    edit.commit();
                    SplashScreen.this.startActivity(new Intent(SplashScreen.this, ChooseLanguage.class));
                    SplashScreen.this.finish();
//                    ReplceFontForEnglish.replceEnglishFont(SplashScreen.this,"DEFAULT","Roboto-Regular.ttf");
                } else

                {
                    SplashScreen.this.startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                    SplashScreen.this.finish();
                }
            }}, secondsDelayed * 3000);}
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
