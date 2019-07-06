package com.evgendev.colorlines;

import java.util.ArrayList;

public class ColorLines {

    private int fieldSize; //Размер игрового поля
    private int [][]field; //Игровое поле (0 - пусто, >0 - цвет шара)
    private int []nextColors; //Цвет шаров, которые появятся при следующем ходе
    private int []selectedBall = {-1,-1}; //Выбранные шар
    private int score = 0; //Набранные очки
    private int colorsCount = 7; //Кол-во всех цветов
    private int nextBallsCount = 3; //Кол-во генерируемых шаров за раз
    private int collapseCount = 5; //Кол-во шаров, которое нужно собрать в линию
    private boolean gameOver = false;

    public ColorLines(int fieldSize) {
        this.fieldSize = fieldSize;
        field = new int[fieldSize][fieldSize];
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                field[i][j] = 0;
            }
        }
    }

    public ColorLines(int fieldSize, int colorsCount, int nextBallsCount, int collapseCount) {
        this(fieldSize);
        this.colorsCount = colorsCount;
        this.nextBallsCount = nextBallsCount;
        this.collapseCount = collapseCount;
    }

    public void moveBall(int posX, int posY){
        nextColors = generateNextColors(nextBallsCount,colorsCount);
        field = addBalls(field,nextColors);
    }

    private int []generateNextColors(int balls, int colors){
        int []arr = new int[balls];
        for (int i = 0; i < balls; i++) {
            arr[i] = rnd(1,colors);
        }
        return arr;
    }

    private int [][]addBalls(int [][]oldField, int []newBalls){
        ArrayList<Integer> freeIndex = new ArrayList<>();
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                if (oldField[i][j]==0){
                    freeIndex.add(fieldSize*i+j);
                }
            }
        }
        for (int i = 0; i < newBalls.length; i++) {
            if (freeIndex.size()<=0) break;
            int rndIndex = rnd(0,freeIndex.size()-1);
            oldField[freeIndex.get(rndIndex)/fieldSize][freeIndex.get(rndIndex)-((freeIndex.get(rndIndex)/fieldSize)*fieldSize)] = newBalls[i];
            freeIndex.remove(rndIndex);
        }
        return oldField;
    }

    public static int rnd(int min, int max)
    {
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }

    public int getFieldSize() {
        return fieldSize;
    }

    public int[][] getField() {
        return field;
    }

    public int[] getNextColors() {
        return nextColors;
    }

    public int[] getSelectedBall() {
        return selectedBall;
    }

    public int getScore() {
        return score;
    }

    public int getColorsCount() {
        return colorsCount;
    }

    public int getNextBallsCount() {
        return nextBallsCount;
    }

    public int getCollapseCount() {
        return collapseCount;
    }

    public boolean isGameOver() {
        return gameOver;
    }
}
