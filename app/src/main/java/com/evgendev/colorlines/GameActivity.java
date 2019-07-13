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
import android.widget.Toast;

public class GameActivity extends AppCompatActivity implements View.OnTouchListener{

    private CLinesSurfaceView cLinesSurfaceView;
    private ColorLines colorLines;
    private int fieldSize = 9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        cLinesSurfaceView = findViewById(R.id.gameView);
        cLinesSurfaceView.setOnTouchListener(this);
        colorLines = (ColorLines) getIntent().getSerializableExtra("colorlines");
        fieldSize = colorLines.getFieldSize();
        cLinesSurfaceView.setFieldSize(fieldSize);
        cLinesSurfaceView.setColorsCount(colorLines.getColorsCount());
        cLinesSurfaceView.setFieldColor(Color.argb(255,200,200,200));
        cLinesSurfaceView.setGridStroke(5);
        new Thread(){
            @Override
            public void run() {
                while (!cLinesSurfaceView.isCanvasReady()){
                }
                cLinesSurfaceView.drawField(colorLines.getField(),colorLines.getSelectedBall());

            }
        }.start();
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(v instanceof SurfaceView){
            float x = event.getX();
            float y = event.getY();
            int []coord = cLinesSurfaceView.getCellXY(x,y);
            if (coord[0]>=fieldSize || coord[1]>=fieldSize) return false;
            switch (colorLines.moveBall(coord[0],coord[1])){
                case 0:
                    break;
                case -1:
                    Toast toast = Toast.makeText(getApplicationContext(),
                            R.string.textCantMove,
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.show();
                    break;
                    default:
                        long mills = 100L;
                        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        if (vibrator.hasVibrator()) {
                            vibrator.vibrate(mills);
                        }
            }
            cLinesSurfaceView.drawField(colorLines.getField(),colorLines.getSelectedBall());
            Log.e("GSV","score: "+colorLines.getScore());
        }
        return false;
    }

}
