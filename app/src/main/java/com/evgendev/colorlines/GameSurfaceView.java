package com.evgendev.colorlines;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder surfaceHolder = null;
    private int backColor = Color.WHITE; //Цвет всей области рисования
    private int fieldColor = Color.WHITE; //Цвет поля
    private int gridColor = Color.BLACK; //Цвет сетки
    private boolean showGrid = true; //Отображать сетку
    private int gridStroke = 1; //Толщина линий сетки
    private int fieldSize = 1; //Кол-во ячеек в строке и столбце

    public enum CellType{
        SQUARE,
        CIRCLE
    }

    public GameSurfaceView(Context context, AttributeSet attrs) {
        super(context,attrs);
        setFocusable(true);
        if(surfaceHolder == null) {
            surfaceHolder = getHolder();
            surfaceHolder.addCallback(this);
        }
        this.setBackgroundColor(backColor);
        this.setZOrderOnTop(true);
        this.getHolder().setFormat(PixelFormat.TRANSLUCENT);
    }

    //Заполняем ячейку поля выбранным графическим символом
    public void setCell(int posX, int posY,CellType type, int color, int procentSize){
        if (posX >= fieldSize || posY>= fieldSize) return;
        Canvas canvas = surfaceHolder.lockCanvas();
        if (canvas == null){
            Log.e("GSV","canvas not ready");
            return;
        }
        drawOrigin(canvas);
        setCell(canvas,posX,posY,type,color,procentSize);
        float maxSize;
        if (this.getWidth()<this.getHeight()){
            maxSize = this.getWidth();
        } else maxSize = this.getHeight();
        if (showGrid) drawGrid(canvas,maxSize);//Рисуем сетку, если нужно
        surfaceHolder.unlockCanvasAndPost(canvas);

    }

    //Заполняем ячейку поля выбранным графическим символом (принимает Canvas на котором будет рисовать)
    public void setCell(Canvas canvas, int posX, int posY,CellType type, int color, int procentSize){
        if (posX >= fieldSize || posY>= fieldSize) return;
        float maxSize;
        if (this.getWidth()<this.getHeight()){
            maxSize = this.getWidth();
        } else maxSize = this.getHeight();
        float stepSize = maxSize / (float) fieldSize;
        if (type == CellType.CIRCLE){//Рисовать круг в ячейке
            float x = (stepSize * posX) + stepSize/2;
            float y = (stepSize * posY) + stepSize/2;
            Paint paint = new Paint();
            paint.setColor(color);
            canvas.drawCircle(x,y,(stepSize*(float)(procentSize/(float)100))/2,paint);
        } else if (type == CellType.SQUARE){//Рисовать квадрат в ячейке
            float centerX = (stepSize * posX) + stepSize/2;
            float centerY = (stepSize * posY) + stepSize/2;
            float startX = (posX*stepSize - centerX)*((float)procentSize/(float)100);
            float startY = (posY*stepSize - centerY)*((float)procentSize/(float)100);
            float stopX = ((posX+1)*stepSize - centerX)*((float)procentSize/(float)100);
            float stopY = ((posY+1)*stepSize - centerY)*((float)procentSize/(float)100);
            startX+=centerX; stopX+=centerX; startY+=centerY; stopY+=centerY;
            Paint paint = new Paint();
            paint.setColor(color);
            canvas.drawRect(startX,startY,stopX,stopY,paint);
        }

    }

    //Узнать индекс ячейки по ее координатам
    public int[] getCellXY(float x, float y){
        float maxSize;
        if (this.getWidth()<this.getHeight()){
            maxSize = this.getWidth();
        } else maxSize = this.getHeight();
        float stepSize = maxSize / (float) fieldSize;
        int []out = new int[2];
        out[0] = (int)(x/stepSize)>=fieldSize ? fieldSize-1 : (int)(x/stepSize);
        out[1] = (int)(y/stepSize)>=fieldSize ? fieldSize-1 : (int)(y/stepSize);
        return out;
    }

    public int getFieldSize() {
        return fieldSize;
    }

    public void setFieldSize(int fieldSize) {
        this.fieldSize = fieldSize;
    }




    public int getFieldColor() {
        return fieldColor;
    }

    public void setFieldColor(int fieldColor) {
        this.fieldColor = fieldColor;
    }

    public boolean isShowGrid() {
        return showGrid;
    }

    public void setShowGrid(boolean showGrid) {
        this.showGrid = showGrid;
    }

    public int getGridStroke() {
        return gridStroke;
    }

    public void setGridStroke(int gridStroke) {
        this.gridStroke = gridStroke;
    }

    public int getBackColor() {
        return backColor;
    }

    public void setBackColor(int backColor) {
        this.backColor = backColor;
    }

    public int getGridColor() {
        return gridColor;
    }

    public void setGridColor(int gridColor) {
        this.gridColor = gridColor;
    }

    //Первоначальные настройки
    public void drawOrigin(){
        Canvas canvas = surfaceHolder.lockCanvas();
        if (canvas == null){
            Log.e("GSV","canvas not ready");
            return;
        }
        drawOrigin(canvas);
        surfaceHolder.unlockCanvasAndPost(canvas);
    }

    //Первоначальные настройки (принимает Canvas, на котором будет рисовать)
    public void drawOrigin(Canvas canvas){
        this.setBackgroundColor(backColor);
        float maxSize;
        if (this.getWidth()<this.getHeight()){
            maxSize = this.getWidth();
        } else maxSize = this.getHeight();
        if (canvas == null){
            Log.e("GSV","canvas not ready");
            return;
        }
        Paint fcol = new Paint();
        fcol.setColor(fieldColor);
        canvas.drawRect(0,0,maxSize,maxSize, fcol);
    }

    //Рисовать сетку (принимает Canvas, на котором будет рисовать)
    public void drawGrid(Canvas canvas, float maxSize){
        if (canvas == null){
            Log.e("GSV","canvas not ready");
            return;
        }
        Paint surfaceBackground = new Paint();
        surfaceBackground.setColor(gridColor);
        surfaceBackground.setStrokeWidth(gridStroke);
        if (this.getWidth()<this.getHeight()){
            maxSize = this.getWidth();
        } else maxSize = this.getHeight();
        float stepSize = maxSize / (float) fieldSize;
        for (int i = 0; i <= fieldSize; i++) {
            canvas.drawLine(0,(float)(stepSize*(float)i),maxSize,(float)stepSize*(float)i,surfaceBackground);
            canvas.drawLine((float)(stepSize*(float)i),0,(float)(stepSize*(float)i),maxSize,surfaceBackground);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        drawOrigin();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}

