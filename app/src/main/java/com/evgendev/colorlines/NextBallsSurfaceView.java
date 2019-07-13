package com.evgendev.colorlines;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

public class NextBallsSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    protected SurfaceHolder surfaceHolder = null;
    protected int backColor = Color.WHITE; //Цвет всей области рисования
    private int colorsCount = 1;
    private int[]lastBalls = null;
    private ArrayList<Integer> colors;
    private volatile boolean canvasReady = false;

    public NextBallsSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);
        if(surfaceHolder == null) {
            surfaceHolder = getHolder();
            surfaceHolder.addCallback(this);
        }
        this.setBackgroundColor(backColor);
        this.setZOrderOnTop(true);
        this.getHolder().setFormat(PixelFormat.TRANSLUCENT);
    }

    public void drawBalls(int []balls){
        float posY = getHeight()/2;
        lastBalls = new int[balls.length];
        ArrayList<Float> posX = makePosY(balls.length,getWidth());
        Canvas canvas = surfaceHolder.lockCanvas();
        float radius;
        if (posY<posX.get(0)){
            radius = posY;
        }else radius = posX.get(0);
        for (int i = 0; i < balls.length; i++) {
            int color = colors.get(balls[i]-1);
            Paint paint = new Paint();
            paint.setColor(color);
            canvas.drawCircle(posX.get(i),posY,radius,paint);
        }
        System.arraycopy(balls,0,lastBalls,0,balls.length);
        surfaceHolder.unlockCanvasAndPost(canvas);
    }

    public boolean isCanvasReady() {
        return canvasReady;
    }

    private ArrayList<Float> makePosY(int count, float size){
        ArrayList<Float> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(((size/(float)count)*(float)i)+((size/(float)count))/2);
        }
        return list;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        canvasReady = true;
        if (lastBalls == null) return;
        drawBalls(lastBalls);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        canvasReady = false;
    }

    public void setColorsCount(int colorsCount) {
        this.colorsCount = colorsCount;
        colors = new ArrayList<>();
        float interval = 360 / (colorsCount);
        float []normal = new float[3];
        for (float x = 0; x < 360; x += interval) {
            normal[0] = x;
            normal[1] = 0.7f;
            normal[2] = 1;
            colors.add(Color.HSVToColor(normal));
        }
    }

}
