package com.senamoviles.alcayata.alcayata;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends Activity {

    private static int SPLASH_TIME_OUT = 3000;
    @BindView(R.id.splash)
    RelativeLayout splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //  Initialize SharedPreferences
                        SharedPreferences getPrefs = PreferenceManager
                                .getDefaultSharedPreferences(getBaseContext());

                        //  Create a new boolean and preference and set it to true
                        boolean isFirstStart = getPrefs.getBoolean("firstStart", true);

                        //  If the activity has never started before...
                        if (isFirstStart) {

                            //  Launch app intro
                            final Intent i = new Intent(SplashActivity.this, Bienvenida.class);

                            runOnUiThread(new Runnable() {
                                @Override public void run() {
                                    startActivity(i);
                                }
                            });

                            //  Make a new preferences editor
                            SharedPreferences.Editor e = getPrefs.edit();

                            //  Edit preference to make it false because we don't want this to run again
                            e.putBoolean("firstStart", false);

                            //  Apply changes
                            e.apply();
                        }
                        else{
                            Intent i = new Intent(SplashActivity.this, MainActivity.class);
                            startActivity(i);
                        }
                    }
                });

                // Start the thread
                t.start();
                finish();
            }
        }, SPLASH_TIME_OUT);



    }
}
