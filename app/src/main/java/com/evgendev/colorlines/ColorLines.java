package com.evgendev.colorlines;

public class ColorLines {

    private int fieldSize; //Размер игрового поля
    private int [][]field; //Игровое поле (0 - пусто, >0 - цвет шара)
    private int []nextColors; //Цвет шаров, которые появятся при следующем ходе
    private int score = 0; //Набранные очки
    private int colorsCount = 7; //Кол-во всех цветов
    private int nextBallsCount = 3; //Кол-во генерируемых шаров за раз
    private int collapseCount = 5; //Кол-во шаров, которое нужно собрать в линию

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

    private int []generateNextColors(int balls, int colors){
        int []arr = new int[balls];
        for (int i = 0; i < balls; i++) {
            arr[i] = rnd(1,colors);
        }
        return arr;
    }

    public static int rnd(int min, int max)
    {
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }
}
