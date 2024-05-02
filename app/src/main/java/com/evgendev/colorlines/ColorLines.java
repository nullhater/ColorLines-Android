package com.evgendev.colorlines;


import android.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;

public class ColorLines implements Serializable {

    private int fieldSize = 9; //Размер игрового поля
    private int [][]field; //Игровое поле (0 - пусто, >0 - цвет шара)
    private int []nextColors; //Цвет шаров, которые появятся при следующем ходе
    private int []selectedBall = {-1,-1}; //Выбранные шар
    private ArrayList<Pair<Integer,Integer>> deleteBalls;
    private int score = 0; //Набранные очки
    private int colorsCount = 7; //Кол-во всех цветов
    private int nextBallsCount = 3; //Кол-во генерируемых шаров за раз
    private int collapseCount = 5; //Кол-во шаров, которое нужно собрать в линию
    private boolean gameOver = false;

    public ColorLines() {
        field = new int[fieldSize][fieldSize];
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                field[i][j] = 0;
            }
        }
        nextColors = generateNextColors(nextBallsCount,colorsCount);
        field = addBalls(field,nextColors);
        nextColors = generateNextColors(nextBallsCount,colorsCount);
    }

    public ColorLines(int fieldSize) {
        this.fieldSize = fieldSize;
        field = new int[fieldSize][fieldSize];
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                field[i][j] = 0;
            }
        }
        nextColors = generateNextColors(nextBallsCount,colorsCount);
        field = addBalls(field,nextColors);
        nextColors = generateNextColors(nextBallsCount,colorsCount);
    }

    public ColorLines(int fieldSize, int colorsCount, int nextBallsCount, int collapseCount) {
        this.fieldSize = fieldSize;
        field = new int[fieldSize][fieldSize];
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                field[i][j] = 0;
            }
        }
        this.colorsCount = colorsCount;
        this.nextBallsCount = nextBallsCount;
        this.collapseCount = collapseCount;
        nextColors = generateNextColors(nextBallsCount,colorsCount);
        field = addBalls(field,nextColors);
        nextColors = generateNextColors(nextBallsCount,colorsCount);
    }

    public ColorLines(ColorLines colorLines){ //Конструктор копирования
        fieldSize = colorLines.fieldSize;
        field = new int[fieldSize][fieldSize];
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                field[i][j] = colorLines.field[i][j];
            }
        }
        score = colorLines.score;
        colorsCount = colorLines.colorsCount;
        nextBallsCount = colorLines.nextBallsCount;
        collapseCount = colorLines.collapseCount;
        gameOver = colorLines.gameOver;
        nextColors = new int[nextBallsCount];
        for (int i = 0; i < nextBallsCount; i++) {
            nextColors[i] = colorLines.nextColors[i];
        }
        selectedBall = new int[2];
        selectedBall[0] = colorLines.selectedBall[0];
        selectedBall[1] = colorLines.selectedBall[1];
    }

    public int moveBall(int posX, int posY){ //Перемещение шара по команде пользователя
        if (gameOver) return 0; //Если игры закончена, то выходим
        if (posX>=fieldSize || posY>=fieldSize || posX<0 || posY<0) return 0; //Если выделение снаружи поля
        if (field[posX][posY]!=0){ //Если игрок выделяет шар
            selectedBall[0] = posX;
            selectedBall[1] = posY;
            return 0;
        }else { //Если игрок выделяет пустую клетку
            if (selectedBall[0]==-1 || selectedBall[1]==-1) return 0; //Если до этого не было что либо выделено
            if (field[selectedBall[0]][selectedBall[1]]==0) return 0;//Если до этого была выделена пустая клетка
            int lastScore = score;
            PathFinder pathFinder = new PathFinder(this,posX,posY);
            if (!pathFinder.quickCheck()){//Если быстрый быстрый поиск пути дал результат, то используем полный поиск пути
                LoopChecker.init();
                LoopChecker.add(selectedBall[0]+" "+selectedBall[1],0);
                PathFinder.init();
                if(!pathFinder.check()) return -1;
            }
            int buf = field[selectedBall[0]][selectedBall[1]];
            field[selectedBall[0]][selectedBall[1]] = 0;
            field[posX][posY] = buf;
            selectedBall[0] = -1;
            selectedBall[1] = -1;
            int tempScore = checkLines();
            if (tempScore>0){
                score+=tempScore;
            }else {
                int tscore=0;
                field = addBalls(field,nextColors);
                nextColors = generateNextColors(nextBallsCount,colorsCount);
                tscore = checkLines();
                score+=tscore;
            }
            if (fieldIsEmpty()){
                int tscore=0;
                do{
                    field = addBalls(field,nextColors);
                    nextColors = generateNextColors(nextBallsCount,colorsCount);
                    tscore = checkLines();
                    score+=tscore;
                }while (tscore>0);
            }
            checkGaveOver();
            if (score>lastScore) return 1; else return 0;
        }
    }

    private boolean fieldIsEmpty(){ //Проверка на пустое поле
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                if (field[i][j]!=0) return false;
            }
        }
        return true;
    }

    public int checkLines(){ //Проверка собранных линий и их удаление с начислением очков
        deleteBalls = new ArrayList<>();
        ArrayList<Pair<Integer,Integer>> sequence = new ArrayList<>();
        for (int i = 0; i < fieldSize; i++) {
            int lastVal = 0;
            for (int j = 0; j < fieldSize; j++) {
                if (j==0){
                    lastVal = field[i][j];
                    if (field[i][j]!=0) sequence.add(new Pair<Integer, Integer>(i,j));
                }else {
                    if (lastVal==field[i][j]){
                        if (field[i][j]!=0) sequence.add(new Pair<Integer, Integer>(i,j));
                    }else {
                        if (sequence.size()==0){
                            if (field[i][j]!=0) sequence.add(new Pair<Integer, Integer>(i,j));
                        }else {
                            deleteBalls(sequence);
                            sequence.clear();
                            if (field[i][j]!=0) sequence.add(new Pair<Integer, Integer>(i,j));
                        }

                    }
                    lastVal=field[i][j];
                }
            }
            deleteBalls(sequence);
            sequence.clear();
        }

        for (int i = 0; i < fieldSize; i++) {
            int lastVal = 0;
            for (int j = 0; j < fieldSize; j++) {
                if (j==0){
                    lastVal = field[j][i];
                    if (field[j][i]!=0) sequence.add(new Pair<Integer, Integer>(j,i));
                }else {
                    if (lastVal==field[j][i]){
                        if (field[j][i]!=0) sequence.add(new Pair<Integer, Integer>(j,i));
                    }else {
                        if (sequence.size()==0){
                            if (field[j][i]!=0) sequence.add(new Pair<Integer, Integer>(j,i));
                        }else {
                            deleteBalls(sequence);
                            sequence.clear();
                            if (field[j][i]!=0) sequence.add(new Pair<Integer, Integer>(j,i));
                        }

                    }
                    lastVal=field[j][i];
                }
            }
            deleteBalls(sequence);
            sequence.clear();
        }

        for (int i = 0; i <= fieldSize - collapseCount; i++) {
            for (int j = 0; j < fieldSize - collapseCount+1; j++) {
                int lastVal = 0;
                for (int k = 0; k < fieldSize; k++) {
                    int x = i+k; int y = j+k;
                    if (i+k>=fieldSize || j+k>=fieldSize) break;
                    if (k==0){
                        lastVal = field[i+k][j+k];
                        if (field[i+k][j+k]!=0)
                            sequence.add(new Pair<Integer, Integer>(i+k,j+k));
                    }else {
                        if (lastVal==field[i+k][j+k]){
                            if (field[i+k][j+k]!=0)
                                sequence.add(new Pair<Integer, Integer>(i+k,j+k));
                        }else {
                            if (sequence.size()==0){
                                if (field[i+k][j+k]!=0)
                                    sequence.add(new Pair<Integer, Integer>(i+k,j+k));
                            }else {
                                deleteBalls(sequence);
                                sequence.clear();
                                if (field[i+k][j+k]!=0)
                                    sequence.add(new Pair<Integer, Integer>(i+k,j+k));
                            }

                        }
                        lastVal=field[i+k][j+k];
                    }
                }
                deleteBalls(sequence);
                sequence.clear();
            }
            deleteBalls(sequence);
            sequence.clear();
        }

        for (int i = 0; i <= fieldSize - collapseCount; i++) {
            for (int j = fieldSize-1; j >= collapseCount-1; j--) {
                int lastVal = 0;
                for (int k = 0; k < fieldSize; k++) {
                    if (i+k>=fieldSize || j-k<0) break;
                    if (k==0){
                        lastVal = field[i+k][j-k];
                        if (field[i+k][j-k]!=0)
                            sequence.add(new Pair<Integer, Integer>(i+k,j-k));
                    }else {
                        if (lastVal==field[i+k][j-k]){
                            if (field[i+k][j-k]!=0)
                                sequence.add(new Pair<Integer, Integer>(i+k,j-k));
                        }else {
                            if (sequence.size()==0){
                                if (field[i+k][j-k]!=0)
                                    sequence.add(new Pair<Integer, Integer>(i+k,j-k));
                            }else {
                                deleteBalls(sequence);
                                sequence.clear();
                                if (field[i+k][j-k]!=0)
                                    sequence.add(new Pair<Integer, Integer>(i+k,j-k));
                            }

                        }
                        lastVal=field[i+k][j-k];
                    }
                }
                deleteBalls(sequence);
                sequence.clear();
            }
            deleteBalls(sequence);
            sequence.clear();
        }
        return removeBallsGetScore();
    }

    private void checkGaveOver(){
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                if (field[i][j]==0) return;
            }
        }
        gameOver = true;
    }

    private void deleteBalls(ArrayList<Pair<Integer,Integer>> position){
        if (position.size()>=collapseCount){
            for (int i = 0; i < position.size() - 1; i++) {
                for (int j = i+1; j < position.size(); j++) {
                    if (field[position.get(i).first][position.get(i).second]!=field[position.get(j).first][position.get(j).second])
                        return ; //Если в линии разные шары, то не удалять
                }
            }
            for (int i = 0; i < position.size(); i++) {
                //field[position.get(i).first][position.get(i).second] = 0;
                Pair<Integer, Integer> pair = new Pair<Integer, Integer>(position.get(i).first,position.get(i).second);
                if (!deleteBalls.contains(pair)){
                    deleteBalls.add(pair);
                }
            }
            //return position.size()*2;
        }
        //return 0;
    }

    private int removeBallsGetScore(){
        int score = deleteBalls.size();
        for (int i = 0; i < deleteBalls.size(); i++) {
            field[deleteBalls.get(i).first][deleteBalls.get(i).second] = 0;
        }
        return score*2;
    }


    private int []generateNextColors(int balls, int colors){ //Генерировать цваета следующих шаров
        int []arr = new int[balls];
        for (int i = 0; i < balls; i++) {
            arr[i] = rnd(1,colors);
        }
        return arr;
    }

    private int [][]addBalls(int [][]oldField, int []newBalls){ //Добавление на поле новых шаров
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
