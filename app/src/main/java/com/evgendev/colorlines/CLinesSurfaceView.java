package com.evgendev.colorlines;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;

import java.util.ArrayList;

public class CLinesSurfaceView extends GameSurfaceView {

    private int colorsCount = 1;
    private ArrayList<BallColor> colors;
    public CLinesSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean drawField(int [][]gameField, int []selectedBall){
        Canvas canvas = surfaceHolder.lockCanvas();
        drawOrigin(canvas);
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                int color = 0;
                if (i == selectedBall[0] && j == selectedBall[1]){
                    color = colors.get(gameField[i][j]).selected;
                }else {
                    color = colors.get(gameField[i][j]).normal;
                }
                setCell(canvas,i,j,CellType.CIRCLE,color,80);
            }
        }
        if (showGrid) drawGrid(canvas,fieldSize);
        surfaceHolder.unlockCanvasAndPost(canvas);
        return true;
    }

    public int getColorsCount() {
        return colorsCount;
    }

    public void setColorsCount(int colorsCount) {
        this.colorsCount = colorsCount;
        colors = new ArrayList<>();
        float interval = 360 / (colorsCount);
        float []normal = new float[3];
        float []selected = new float[3];
        for (float x = 0; x < 360; x += interval) {
            normal[0] = x / 360;
            normal[1] = 1;
            normal[2] = 1;
            selected[0] = x / 360;
            selected[1] = 1;
            selected[2] = 1;
            colors.add(new BallColor(Color.HSVToColor(normal),Color.HSVToColor(selected)));
        }
    }

    private class BallColor {
        public BallColor(int normal, int selected) {
            this.normal = normal;
            this.selected = selected;
        }
        public int normal = 0;
        public int selected = 0;
    }
}
