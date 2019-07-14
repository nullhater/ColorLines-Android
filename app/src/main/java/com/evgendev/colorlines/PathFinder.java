package com.evgendev.colorlines;

import android.util.Log;

public class PathFinder {

    private ColorLines colorLines;
    private int finishX;
    private int finishY;
    private static int deep = 0;

    public PathFinder(ColorLines colorLines, int finishX, int finishY) {
        this.colorLines = new ColorLines(colorLines);
        this.finishX = finishX;
        this.finishY = finishY;
    }

    public static void init(){
        deep = 0;
    }

    public boolean quickCheck(){
        if (finishX == colorLines.getSelectedBall()[0]){
            int beginY, endY;
            if (finishY>colorLines.getSelectedBall()[1]){
                beginY = colorLines.getSelectedBall()[1];
                endY = finishY;
            }else {
                endY = colorLines.getSelectedBall()[1];
                beginY = finishY;
            }
            for (int i = beginY+1; i < endY; i++) {
                if (colorLines.getField()[finishX][i]!=0) return false;
            }
            return true;
        }

        if (finishY == colorLines.getSelectedBall()[1]){
            int beginX, endX;
            if (finishX>colorLines.getSelectedBall()[0]){
                beginX = colorLines.getSelectedBall()[0];
                endX = finishX;
            }else {
                endX = colorLines.getSelectedBall()[0];
                beginX = finishX;
            }
            for (int i = beginX+1; i < endX; i++) {
                if (colorLines.getField()[i][finishY]!=0) return false;
            }
            return true;
        }
        return false;
    }

    public boolean check(){
        deep++;
        ColorLines tempCLines = new ColorLines(colorLines);
        for (int i = 0; i < 4; i++) {
            if (move(colorLines,i)){
                if (onPlace(colorLines)){
                    colorLines = null;
                    System.gc();
                    colorLines = new ColorLines(tempCLines);
                    return true;
                }else {
                    String currentStatus = colorLines.getSelectedBall()[0]+" "+colorLines.getSelectedBall()[1];
                    if (LoopChecker.isLoop(currentStatus,deep)){
                        colorLines = null;
                        colorLines = new ColorLines(tempCLines);
                    }else {
                        LoopChecker.add(currentStatus,deep);
                        PathFinder pathFinder = new PathFinder(colorLines,finishX,finishY);
                        if (pathFinder.check()) return true;
                        colorLines = null;
                        colorLines = new ColorLines(tempCLines);
                    }
                }
            }else {
                colorLines = null;
                colorLines = new ColorLines(tempCLines);
            }
        }
        deep--;
        return false;
    }

    public boolean move(ColorLines colorLines, int vector){ // 0 - вверх, 1 - вправо, 2 - вниз, 3 - влево
        int posX = colorLines.getSelectedBall()[0];
        int posY = colorLines.getSelectedBall()[1];
        switch (vector){
            case 0:
                posY-=1;
                break;
            case 1:
                posX+=1;
                break;
            case 2:
                posY+=1;
                break;
            case 3:
                posX-=1;
                break;
                default:
                    return false;
        }
        if (posX<0 || posX>=colorLines.getFieldSize() || posY<0 || posY>=colorLines.getFieldSize()) return false;
        if (colorLines.getField()[posX][posY]!=0){
            return false;
        }else {
            int temp = colorLines.getField()[colorLines.getSelectedBall()[0]][colorLines.getSelectedBall()[1]];
            colorLines.getField()[colorLines.getSelectedBall()[0]][colorLines.getSelectedBall()[1]] = 0;
            colorLines.getSelectedBall()[0] = posX;
            colorLines.getSelectedBall()[1] = posY;
            colorLines.getField()[posX][posY] = temp;
            return true;
        }
    }

    public boolean onPlace(ColorLines colorLines){
        if (finishX == colorLines.getSelectedBall()[0] && finishY == colorLines.getSelectedBall()[1]){
            return true;
        }
        return false;
    }
}
