package com.evgendev.colorlines;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.ads.consent.ConsentForm;



public class MainActivity extends AppCompatActivity {

    private Button continueButton;
    private TextView textVersion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, String.valueOf(AppUtils.appPersonilize), Toast.LENGTH_SHORT).show();
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
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }
}
