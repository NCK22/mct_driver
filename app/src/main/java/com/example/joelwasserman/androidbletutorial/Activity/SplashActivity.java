package com.example.joelwasserman.androidbletutorial.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.joelwasserman.androidbletutorial.R;
import com.example.joelwasserman.androidbletutorial.Storage.SPProfile;


public class SplashActivity extends AppCompatActivity {

    SPProfile spProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        spProfile=new SPProfile(this);
        Thread splash= new Thread(){
            public void run(){
                try{
                    Log.e("Thread","started");
                    sleep(3000);

                    if(spProfile.getIsLogin().equalsIgnoreCase("true"))
                            startActivity(new Intent(SplashActivity.this,MainActivity.class));
                                    else
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));

                                    finish();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    finish();
                }
            }

        };
        splash.start();


    }
}
