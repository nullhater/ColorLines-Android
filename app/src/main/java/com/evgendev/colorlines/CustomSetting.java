/*
    Кастомная настройка игры
 */

package com.evgendev.colorlines;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CustomSetting extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener{

    TextView textView = null;
    TextView textCounter = null;
    SeekBar seekBar = null;
    private int downLimit = 4;
    private int upLimit = 20;
    private int stage = 0;
    private int fieldSize;
    private int collapseCount;
    private int nextBallsCount;
    private int colorsCount;
    private AdView adView;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_custom_setting);
        sharedPreferences = getSharedPreferences(AppUtils.APP_PREFERENCES, Context.MODE_PRIVATE);
        textView = findViewById(R.id.textViewCustom);
        textCounter = findViewById(R.id.textViewCounter);
        adView = findViewById(R.id.adViewBanner2);
        seekBar = findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(this);
        textCounter.setText(String.valueOf(changeRange(seekBar.getProgress(),0,100,downLimit,upLimit)));
        textView.setText(R.string.textSetFieldSize);
        if (AppUtils.appAlreadyInit){
            showAd();
        }else {
            MobileAds.initialize(this, new OnInitializationCompleteListener() {
                @Override
                public void onInitializationComplete(InitializationStatus initializationStatus) {
                    if (AppUtils.appPersonilize == false) {
                        Bundle extras = new Bundle();
                        extras.putString("npa", "1");
                        AppUtils.request = new AdRequest.Builder()
                                .addNetworkExtrasBundle(AdMobAdapter.class, extras)
//                                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
//                                .addTestDevice("EA105D23EC536425B8F30684DD8AE52F")
//                                .addTestDevice("F492843A2D4A38671941ECC971232B35")
                                .build();
                    } else {
                        AppUtils.request = new AdRequest.Builder()
//                                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
//                                .addTestDevice("EA105D23EC536425B8F30684DD8AE52F")
//                                .addTestDevice("F492843A2D4A38671941ECC971232B35")
                                .build();
                    }
                    AppUtils.appAlreadyInit = true;
                    showAd();
                }
            });
        }

        adView.setAdListener(new AdListener(){
            @Override
            public void onAdOpened() {
                int clickCount;
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if(sharedPreferences.contains(AppUtils.CLICK_COUNT)){
                    clickCount = sharedPreferences.getInt(AppUtils.CLICK_COUNT,0);
                }else {
                    clickCount = 0;
                }
                clickCount++;
                if (clickCount>=2){
                    Date currentDate = new Date();
                    Calendar c = Calendar.getInstance();
                    c.setTime(currentDate);
                    c.add(Calendar.DATE, 1);
                    currentDate = c.getTime();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    editor.putString(AppUtils.AD_NEXT_DATE,dateFormat.format(currentDate));
                    editor.putInt(AppUtils.CLICK_COUNT,0);
                    editor.apply();
                    adView.setEnabled(false);
                    adView.setVisibility(View.GONE);
                }else {
                    editor.putInt(AppUtils.CLICK_COUNT,clickCount);
                    editor.apply();
                }
            }
        });
    }

    public void showAd(){
        if (sharedPreferences.contains(AppUtils.AD_NEXT_DATE)){
            String strDate = sharedPreferences.getString(AppUtils.AD_NEXT_DATE,"");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date currentDate = new Date();
            Date date;
            try {
                date = dateFormat.parse(strDate);
            } catch (ParseException e) {
                adView.loadAd(AppUtils.request);
                return;
            }
            if (date.compareTo(currentDate)==1){
                adView.setEnabled(false);
                adView.setVisibility(View.GONE);
            }else {
                adView.loadAd(AppUtils.request);
            }
        }else {
            adView.loadAd(AppUtils.request);
        }
    }

    public void onClickNext(View view) {
        switch (stage){
            case 0:
                fieldSize = changeRange(seekBar.getProgress(),0,100,downLimit,upLimit);
                textView.setText(R.string.textCollapseCount);
                downLimit = 2;
                upLimit = fieldSize;
                textCounter.setText(String.valueOf(changeRange(seekBar.getProgress(),0,100,downLimit,upLimit)));
                break;
            case 1:
                collapseCount = changeRange(seekBar.getProgress(),0,100,downLimit,upLimit);
                textView.setText(R.string.textNextBallsCount);
                downLimit = 1;
                upLimit = collapseCount-1;
                textCounter.setText(String.valueOf(changeRange(seekBar.getProgress(),0,100,downLimit,upLimit)));
                break;
            case 2:
                nextBallsCount = changeRange(seekBar.getProgress(),0,100,downLimit,upLimit);
                textView.setText(R.string.textColorsCount);
                downLimit = 2;
                upLimit = 10;
                textCounter.setText(String.valueOf(changeRange(seekBar.getProgress(),0,100,downLimit,upLimit)));
                break;
            case 3:
                colorsCount = changeRange(seekBar.getProgress(),0,100,downLimit,upLimit);
                ColorLines colorLines = new ColorLines(fieldSize, colorsCount,nextBallsCount,collapseCount);
                Intent intent = new Intent(CustomSetting.this,GameActivity.class);
                intent.putExtra("colorlines",colorLines);
                startActivity(intent);
        }
        stage++;
    }

    public void abortAndExit(){
        Context context = CustomSetting.this;
        AlertDialog.Builder ad;
        ad = new AlertDialog.Builder(context);
        ad.setTitle(R.string.textAbortAndExit);
        ad.setMessage(R.string.textAbortCreate);
        ad.setPositiveButton(R.string.textAbort, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(CustomSetting.this,MainActivity.class);
                startActivity(intent);
            }
        });
        ad.setNegativeButton(R.string.textContinue, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        ad.setCancelable(true);
        ad.show();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        textCounter.setText(String.valueOf(changeRange(seekBar.getProgress(),0,100,downLimit,upLimit)));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        textCounter.setText(String.valueOf(changeRange(seekBar.getProgress(),0,100,downLimit,upLimit)));
    }

    private int changeRange(int value, int srcFrom, int srcTo, int from, int to){
        return (int)((double)((value-srcFrom)*(to-from))/(double)(srcTo - srcFrom) + from);
    }

    @Override
    public void onBackPressed() {
        abortAndExit();
    }

    public void onClickToMainAct(View view) {
        abortAndExit();
    }
}
