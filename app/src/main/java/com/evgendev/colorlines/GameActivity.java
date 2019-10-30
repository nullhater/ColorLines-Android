package com.evgendev.colorlines;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Vibrator;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;


public class GameActivity extends AppCompatActivity implements View.OnTouchListener{

    private volatile boolean canvasReady = false;
    private CLinesSurfaceView cLinesSurfaceView;
    private NextBallsSurfaceView nextBallsSurfaceView;
    private TextView textViewScore;
    private ColorLines colorLines;
    private ColorLines cLinesRestore;
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_game);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        cLinesSurfaceView = findViewById(R.id.gameView);
        adView = findViewById(R.id.adViewBanner);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                AdRequest adRequest = new AdRequest.Builder()
                        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR) //TODO УДАЛИТЬ ПЕРЕД ПУБЛИКАЦИЕЙ
                        .build();
                adView.loadAd(adRequest);
            }
        });
        nextBallsSurfaceView = findViewById(R.id.nextBallsSurfaceView);
        nextBallsSurfaceView.setBackColor(getResources().getColor(R.color.backColor));
        textViewScore = findViewById(R.id.textViewScore);
        cLinesSurfaceView.setOnTouchListener(this);
        colorLines = (ColorLines) getIntent().getSerializableExtra("colorlines");
        cLinesRestore = new ColorLines(colorLines);
        cLinesSurfaceView.setFieldSize(colorLines.getFieldSize());
        cLinesSurfaceView.setColorsCount(colorLines.getColorsCount());
        cLinesSurfaceView.setBackColor(getResources().getColor(R.color.backColor));
        cLinesSurfaceView.setFieldColor(getResources().getColor(R.color.backColor));
        cLinesSurfaceView.setGridStroke(5);
        nextBallsSurfaceView.setColorsCount(colorLines.getColorsCount());
        textViewScore.setText(Integer.toString(colorLines.getScore()));
        new Thread(){
            @Override
            public void run() {
                while (!(cLinesSurfaceView.isCanvasReady() && nextBallsSurfaceView.isCanvasReady())){ //Ждем пока canvas будет готов
                }
                cLinesSurfaceView.drawField(colorLines.getField(),colorLines.getSelectedBall());
                nextBallsSurfaceView.drawBalls(colorLines.getNextColors());
                canvasReady = true;

            }
        }.start();
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) { //Касание к полю
        if (!canvasReady) return false;
        if(v instanceof SurfaceView){
            float x = event.getX();
            float y = event.getY();
            int []coord = cLinesSurfaceView.getCellXY(x,y);
            if (coord[0]>=colorLines.getFieldSize() || coord[1]>=colorLines.getFieldSize()) return false;
            switch (colorLines.moveBall(coord[0],coord[1])){
                case 0:
                    nextBallsSurfaceView.drawBalls(colorLines.getNextColors());
                    break;
                case -1:
                    Toast toast = Toast.makeText(getApplicationContext(),
                            R.string.textCantMove,
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM, 0, 200);
                    toast.show();
                    break;
                    default:
                        textViewScore.setText(Integer.toString(colorLines.getScore()));
                        long mills = 100L;
                        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        if (vibrator.hasVibrator()) {
                            vibrator.vibrate(mills);
                        }
                        nextBallsSurfaceView.drawBalls(colorLines.getNextColors());
            }
            cLinesSurfaceView.drawField(colorLines.getField(),colorLines.getSelectedBall());
            if (colorLines.isGameOver()) showWinDialog();
        }
        return false;
    }

    public void onClickRestart(View view){
        restartGame();
    }

    public void restartGame(){
        if (!canvasReady) return;
        colorLines = new ColorLines(cLinesRestore.getFieldSize(),cLinesRestore.getColorsCount(),cLinesRestore.getNextBallsCount(),cLinesRestore.getCollapseCount());
        textViewScore.setText(Integer.toString(colorLines.getScore()));
        cLinesSurfaceView.drawField(colorLines.getField(),colorLines.getSelectedBall());
        nextBallsSurfaceView.drawBalls(colorLines.getNextColors());
    }

    public void onClickCloseGame(View view) {
        showSaveDialog();
    }

    @Override
    public void onBackPressed() {
        showSaveDialog();
    }

    private void showSaveDialog(){
        final Context context = GameActivity.this;
        AlertDialog.Builder ad;
        ad = new AlertDialog.Builder(context);
        ad.setTitle(R.string.textExit);
        ad.setMessage(R.string.textSaveAndExit);
        ad.setPositiveButton(R.string.textYes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AppUtils.objToFile(AppUtils.FILESAVE,colorLines,context);
                startActivity(new Intent(GameActivity.this, MainActivity.class));
            }
        });
        ad.setNegativeButton(R.string.textCancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        ad.setCancelable(false);
        ad.show();
    }

    private void showWinDialog(){
        final Context context = GameActivity.this;
        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
        String out = getResources().getString(R.string.textPoints);
        builder.setTitle(R.string.textGaveOver)
                .setMessage(out+" "+Integer.toString(colorLines.getScore()))
                .setCancelable(false)
                .setNegativeButton(R.string.textExit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AppUtils.objToFile(AppUtils.FILESAVE,new Object(),context);
                        startActivity(new Intent(GameActivity.this, MainActivity.class));
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    protected void onStop() {
        final Context context = GameActivity.this;
        if (!colorLines.isGameOver()) AppUtils.objToFile(AppUtils.FILESAVE,colorLines,context);
        super.onStop();
    }
}
