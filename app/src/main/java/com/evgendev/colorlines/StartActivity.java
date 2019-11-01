package com.evgendev.colorlines;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.ads.consent.ConsentForm;
import com.google.ads.consent.ConsentFormListener;
import com.google.ads.consent.ConsentInfoUpdateListener;
import com.google.ads.consent.ConsentInformation;
import com.google.ads.consent.ConsentStatus;
import com.google.ads.consent.DebugGeography;

import java.net.MalformedURLException;
import java.net.URL;

public class StartActivity extends AppCompatActivity {

    private ConsentForm form;
    private SharedPreferences mSettings;
    private static Object waitAddLoad = new Object();
    private static boolean initStart = false;
    private boolean inEEA = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start);
        mSettings = getSharedPreferences(AppUtils.APP_PREFERENCES, Context.MODE_PRIVATE);
//        if (mSettings.contains(AppUtils.USER_ADULT)) {
//            if (mSettings.getBoolean(AppUtils.USER_ADULT, false)) {
//                AppUtils.appPersonilize = false;
//                startActivity(new Intent(StartActivity.this, MainActivity.class));
//                return;
//            }
//        }
        StartAddWaiter waiter = new StartAddWaiter();
        addInit();
        waiter.execute();
    }


    public void startMainActivity(){
        startActivity(new Intent(StartActivity.this, MainActivity.class));
    }

    public void showAdultDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(getString(R.string.dialogTitle));
        dialog.setMessage(getString(R.string.dialogMessage));
        dialog.setPositiveButton(getString(R.string.dialogIsAdult), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveAdultStatus(true);
                startMainActivity();
            }
        });

        dialog.setNegativeButton(getString(R.string.dialogNoAdult), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AppUtils.appPersonilize = false;
                saveAdultStatus(false);
                Toast.makeText(StartActivity.this, getString(R.string.personalAddDisabled), Toast.LENGTH_SHORT).show();
                startMainActivity();
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }




    private void addInit(){
        String[] publisherIds = {getString(R.string.publishId)};
        ConsentInformation consentInformation = ConsentInformation.getInstance(getApplicationContext());
        consentInformation.addTestDevice("EA105D23EC536425B8F30684DD8AE52F");//TODO УДАЛИТЬ ЭТУ СТРОЧКУ
        consentInformation.addTestDevice("F492843A2D4A38671941ECC971232B35");//TODO УДАЛИТЬ ЭТУ СТРОЧКУ (SONY)
        consentInformation.setDebugGeography(DebugGeography.DEBUG_GEOGRAPHY_EEA); //TODO УДАЛИТЬ ЭТУ СТРОЧКУ
        consentInformation.requestConsentInfoUpdate(publisherIds, new ConsentInfoUpdateListener() {
            @Override
            public void onConsentInfoUpdated(ConsentStatus consentStatus) {
                // User's consent status successfully updated.
                initStart = true;
                if (mSettings.contains(AppUtils.USER_ADULT)){
                    if (!mSettings.getBoolean(AppUtils.USER_ADULT, false)){
                        AppUtils.appPersonilize = false;
                        startMainActivity();
                        return;
                    }
                }
                inEEA = ConsentInformation.getInstance(getApplicationContext()).isRequestLocationInEeaOrUnknown();
                if (inEEA){
                    if (consentStatus == consentStatus.PERSONALIZED){//Если реклама персонализированная
                        AppUtils.appPersonilize = true;
                        checkAge();
                    }else if (consentStatus == consentStatus.NON_PERSONALIZED){//Если реклама не персонализированная
                        AppUtils.appPersonilize = false;
                        startMainActivity();
                    }else{//Если еще не настроен тип рекламы
                        URL privacyUrl = null;
                        try {
                            // TODO: Replace with your app's privacy policy URL.
                            privacyUrl = new URL(getString(R.string.licence_url));
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                            // Handle error.
                        }
                        form = new ConsentForm.Builder(StartActivity.this, privacyUrl)
                                .withListener(new ConsentFormListener() {
                                    @Override
                                    public void onConsentFormLoaded() {
                                        // Consent form loaded successfully.
                                        form.show();
                                    }

                                    @Override
                                    public void onConsentFormOpened() {
                                        // Consent form was displayed.
                                    }

                                    @Override
                                    public void onConsentFormClosed(
                                            ConsentStatus consentStatus, Boolean userPrefersAdFree) {
                                        // Consent form was closed.
                                        if (consentStatus == consentStatus.NON_PERSONALIZED){
                                            AppUtils.appPersonilize = false;
                                        }else if (consentStatus == consentStatus.PERSONALIZED){
                                            AppUtils.appPersonilize = true;
                                        }
                                        checkAge();
                                    }

                                    @Override
                                    public void onConsentFormError(String errorDescription) {
                                        // Consent form error.
                                        Toast.makeText(StartActivity.this, getString(R.string.sww), Toast.LENGTH_LONG).show();
                                        startMainActivity();
                                    }
                                })
                                .withPersonalizedAdsOption()
                                .withNonPersonalizedAdsOption()
                                .build();
                        form.load();
                    }
                }else { //Если пользователь не в ЕС
                    AppUtils.appPersonilize = true;
                    startMainActivity();
                }
            }

            @Override
            public void onFailedToUpdateConsentInfo(String errorDescription) {
                // User's consent status failed to update.
            }
        });
    }

    private void checkAge(){
        if (AppUtils.appPersonilize) {
            if (inEEA) {
                if (mSettings.contains(AppUtils.USER_ADULT)){
                    if (mSettings.getBoolean(AppUtils.USER_ADULT, false)){
                        startMainActivity();
                        return;
                    }
                }
                showAdultDialog();
            }
        }else {
            startMainActivity();
        }
    }

    private void saveAdultStatus(boolean status){
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putBoolean(AppUtils.USER_ADULT,status);
        editor.apply();
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

        @Override
        protected void onPostExecute(Void aVoid) {
            if (!initStart) {
                checkAge();
            }
        }
    }
}
