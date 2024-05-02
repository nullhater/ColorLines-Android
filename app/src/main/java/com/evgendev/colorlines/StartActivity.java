package com.evgendev.colorlines;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;


public class StartActivity extends AppCompatActivity {

    private static Object waitAddLoad = new Object();
    private static boolean initStart = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start);
        StartAddWaiter waiter = new StartAddWaiter();
        startMainActivity();
        waiter.execute();
    }


    public void startMainActivity(){
        startActivity(new Intent(StartActivity.this, MainActivity.class));
    }


    private class StartAddWaiter extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            synchronized (waitAddLoad){
                if (!initStart){
                    try {
                        waitAddLoad.wait(3500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }
    }
}
