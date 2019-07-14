package com.evgendev.colorlines;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import static com.evgendev.colorlines.AppUtils.APP_PREFERENCES;
import static com.evgendev.colorlines.AppUtils.APP_PREFERENCES_SAVEGAME;

public class MainActivity extends AppCompatActivity {

    private Button continueButton;
    private TextView textVersion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        continueButton = findViewById(R.id.buttonContinueG);
        textVersion = findViewById(R.id.textViewVersion);
        textVersion.setText(getResources().getString(R.string.textVersion)+" "+BuildConfig.VERSION_NAME);
        checkSaves();
    }

    public void onClickNewGame(View view) {
        ColorLines colorLines = new ColorLines();
        Intent intent = new Intent(MainActivity.this, GameActivity.class);
        intent.putExtra("colorlines",colorLines);
        startActivity(intent);
    }

    public void onClickCustomGame(View view){
        Intent intent = new Intent(MainActivity.this,CustomSetting.class);
        startActivity(intent);
    }

    public void onClickContinue(View view) {
        final Context context = MainActivity.this;
        Object obj = AppUtils.FileToObj(AppUtils.FILESAVE,context);
        if (obj instanceof ColorLines) {
            ColorLines colorLines = (ColorLines) obj;
            Intent intent = new Intent(MainActivity.this, GameActivity.class);
            intent.putExtra("colorlines", colorLines);
            startActivity(intent);
        }
    }

    private void checkSaves(){
        final Context context = MainActivity.this;
        Object obj = AppUtils.FileToObj(AppUtils.FILESAVE,context);
        if (obj!=null){
            if (obj instanceof ColorLines){
                continueButton.setEnabled(true);
            }else continueButton.setEnabled(false);
        }else continueButton.setEnabled(false);
    }

    @Override
    public void onBackPressed() {
        finishAndRemoveTask();
    }
}
