package com.evgendev.colorlines;

import android.content.Context;
import android.graphics.Color;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity implements View.OnTouchListener{

    private volatile boolean canvasReady = false;
    private CLinesSurfaceView cLinesSurfaceView;
    private NextBallsSurfaceView nextBallsSurfaceView;
    private TextView textViewScore;
    private ColorLines colorLines;
    private ColorLines cLinesRestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        cLinesSurfaceView = findViewById(R.id.gameView);
        nextBallsSurfaceView = findViewById(R.id.nextBallsSurfaceView);
        textViewScore = findViewById(R.id.textViewScore);
        cLinesSurfaceView.setOnTouchListener(this);
        colorLines = (ColorLines) getIntent().getSerializableExtra("colorlines");
        cLinesRestore = new ColorLines(colorLines);
        cLinesSurfaceView.setFieldSize(colorLines.getFieldSize());
        cLinesSurfaceView.setColorsCount(colorLines.getColorsCount());
        cLinesSurfaceView.setFieldColor(Color.argb(255,200,200,200));
        cLinesSurfaceView.setGridStroke(5);
        nextBallsSurfaceView.setColorsCount(colorLines.getColorsCount());
        textViewScore.setText(Integer.toString(colorLines.getScore()));
        new Thread(){
            @Override
            public void run() {
                while (!(cLinesSurfaceView.isCanvasReady() && nextBallsSurfaceView.isCanvasReady())){
                }
                cLinesSurfaceView.drawField(colorLines.getField(),colorLines.getSelectedBall());
                nextBallsSurfaceView.drawBalls(colorLines.getNextColors());
                canvasReady = true;

            }
        }.start();
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
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
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
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
            Log.e("GSV","score: "+colorLines.getScore());
        }
        return false;
    }

    public void onClickRestart(View view){
        restartGame();
    }

    public void restartGame(){
        if (!canvasReady) return;
        colorLines = new ColorLines(cLinesRestore);
        textViewScore.setText(Integer.toString(colorLines.getScore()));
        cLinesSurfaceView.drawField(colorLines.getField(),colorLines.getSelectedBall());
        nextBallsSurfaceView.drawBalls(colorLines.getNextColors());
    }

    public void onClickCloseGame(View view) {

    }
}
