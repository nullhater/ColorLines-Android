package com.evgendev.colorlines;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

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
        cLinesSurfaceView.setFieldSize(fieldSize);
        cLinesSurfaceView.setColorsCount(7);
        cLinesSurfaceView.setFieldColor(Color.argb(255,200,200,200));
        cLinesSurfaceView.setGridStroke(5);
        cLinesSurfaceView.drawOrigin();
        colorLines = new ColorLines(9);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(v instanceof SurfaceView){
            float x = event.getX();
            float y = event.getY();
            int []coord = cLinesSurfaceView.getCellXY(x,y);
            if (coord[0]>=fieldSize || coord[1]>=fieldSize) return false;
            colorLines.moveBall(coord[0],coord[1]);
            cLinesSurfaceView.drawField(colorLines.getField(),colorLines.getSelectedBall());
            Log.e("GSV","score: "+colorLines.getScore());
        }
        return false;
    }

}
