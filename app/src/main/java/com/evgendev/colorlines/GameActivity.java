package com.evgendev.colorlines;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

public class GameActivity extends AppCompatActivity implements View.OnTouchListener{

    private CLinesSurfaceView cLinesSurfaceView;
    private ColorLines colorLines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        cLinesSurfaceView = findViewById(R.id.gameView);
        cLinesSurfaceView.setOnTouchListener(this);
        cLinesSurfaceView.setFieldSize(9);
        cLinesSurfaceView.setColorsCount(7);
        cLinesSurfaceView.setFieldColor(Color.argb(255,100,100,200));
        cLinesSurfaceView.setGridStroke(5);
        cLinesSurfaceView.drawOrigin();
        colorLines = new ColorLines(9);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        colorLines.moveBall(0,0);
        cLinesSurfaceView.drawField(colorLines.getField(),colorLines.getSelectedBall());
        return false;
    }
}
