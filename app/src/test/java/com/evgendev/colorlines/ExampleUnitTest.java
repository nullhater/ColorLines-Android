package com.evgendev.colorlines;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void delete_balls_1(){
        ColorLines colorLines = new ColorLines(9);
        for (int i = 0; i < colorLines.getFieldSize(); i++) {
            for (int j = 0; j < colorLines.getFieldSize(); j++) {
                colorLines.getField()[i][j] = 0;
            }
        }
        colorLines.getField()[0][0] = 1;
        colorLines.getField()[0][1] = 1;
        colorLines.getField()[0][2] = 1;
        colorLines.getField()[0][3] = 1;
        colorLines.getField()[0][4] = 1;
        assertEquals(10,colorLines.checkLines());
    }

    @Test
    public void delete_balls_2(){
        ColorLines colorLines = new ColorLines(9);
        for (int i = 0; i < colorLines.getFieldSize(); i++) {
            for (int j = 0; j < colorLines.getFieldSize(); j++) {
                colorLines.getField()[i][j] = 0;
            }
        }
        colorLines.getField()[0][4] = 6;
        colorLines.getField()[0][5] = 6;
        colorLines.getField()[0][6] = 6;
        colorLines.getField()[0][7] = 6;
        colorLines.getField()[0][8] = 6;
        assertEquals(10,colorLines.checkLines());
    }

    @Test
    public void delete_balls_3(){
        ColorLines colorLines = new ColorLines(9);
        for (int i = 0; i < colorLines.getFieldSize(); i++) {
            for (int j = 0; j < colorLines.getFieldSize(); j++) {
                colorLines.getField()[i][j] = 0;
            }
        }
        colorLines.getField()[2][3] = 6;
        colorLines.getField()[2][4] = 6;
        colorLines.getField()[2][5] = 6;
        colorLines.getField()[2][6] = 6;
        colorLines.getField()[2][7] = 6;
        assertEquals(10,colorLines.checkLines());
    }

    @Test
    public void delete_balls_4(){
        ColorLines colorLines = new ColorLines(9);
        for (int i = 0; i < colorLines.getFieldSize(); i++) {
            for (int j = 0; j < colorLines.getFieldSize(); j++) {
                colorLines.getField()[i][j] = 0;
            }
        }
        colorLines.getField()[2][3] = 6;
        colorLines.getField()[2][4] = 6;
        colorLines.getField()[2][5] = 6;
        colorLines.getField()[2][6] = 6;
        colorLines.getField()[2][7] = 6;
        colorLines.getField()[3][3] = 6;
        assertEquals(10,colorLines.checkLines());
    }

    @Test
    public void delete_balls_5(){
        ColorLines colorLines = new ColorLines(9);
        for (int i = 0; i < colorLines.getFieldSize(); i++) {
            for (int j = 0; j < colorLines.getFieldSize(); j++) {
                colorLines.getField()[i][j] = 0;
            }
        }
        colorLines.getField()[2][0] = 6;
        colorLines.getField()[2][1] = 6;
        colorLines.getField()[2][2] = 6;
        colorLines.getField()[2][3] = 6;
        colorLines.getField()[2][4] = 6;
        colorLines.getField()[2][5] = 6;
        colorLines.getField()[2][6] = 6;
        colorLines.getField()[2][7] = 6;
        colorLines.getField()[2][8] = 6;
        assertEquals(18,colorLines.checkLines());
    }

    @Test
    public void delete_balls_6(){
        ColorLines colorLines = new ColorLines(9);
        for (int i = 0; i < colorLines.getFieldSize(); i++) {
            for (int j = 0; j < colorLines.getFieldSize(); j++) {
                colorLines.getField()[i][j] = 0;
            }
        }
        colorLines.getField()[0][0] = 1;
        colorLines.getField()[1][0] = 1;
        colorLines.getField()[2][0] = 1;
        colorLines.getField()[3][0] = 1;
        colorLines.getField()[4][0] = 1;
        assertEquals(10,colorLines.checkLines());
    }

    @Test
    public void delete_balls_7(){
        ColorLines colorLines = new ColorLines(9);
        for (int i = 0; i < colorLines.getFieldSize(); i++) {
            for (int j = 0; j < colorLines.getFieldSize(); j++) {
                colorLines.getField()[i][j] = 0;
            }
        }
        colorLines.getField()[3][2] = 6;
        colorLines.getField()[4][2] = 6;
        colorLines.getField()[5][2] = 6;
        colorLines.getField()[6][2] = 6;
        colorLines.getField()[7][2] = 6;
        assertEquals(10,colorLines.checkLines());
    }

    @Test
    public void delete_balls_8(){
        ColorLines colorLines = new ColorLines(9);
        for (int i = 0; i < colorLines.getFieldSize(); i++) {
            for (int j = 0; j < colorLines.getFieldSize(); j++) {
                colorLines.getField()[i][j] = 0;
            }
        }
        colorLines.getField()[4][2] = 6;
        colorLines.getField()[5][2] = 6;
        colorLines.getField()[6][2] = 6;
        colorLines.getField()[7][2] = 6;
        colorLines.getField()[8][2] = 6;
        assertEquals(10,colorLines.checkLines());
    }

    @Test
    public void delete_balls_9(){
        ColorLines colorLines = new ColorLines(9);
        for (int i = 0; i < colorLines.getFieldSize(); i++) {
            for (int j = 0; j < colorLines.getFieldSize(); j++) {
                colorLines.getField()[i][j] = 0;
            }
        }
        colorLines.getField()[0][3] = 6;
        colorLines.getField()[1][3] = 6;
        colorLines.getField()[2][3] = 6;
        colorLines.getField()[3][3] = 6;
        colorLines.getField()[4][3] = 6;
        colorLines.getField()[5][3] = 6;
        colorLines.getField()[6][3] = 6;
        colorLines.getField()[7][3] = 6;
        colorLines.getField()[8][3] = 6;
        assertEquals(18,colorLines.checkLines());
    }

    @Test
    public void delete_balls_10(){
        ColorLines colorLines = new ColorLines(9);
        for (int i = 0; i < colorLines.getFieldSize(); i++) {
            for (int j = 0; j < colorLines.getFieldSize(); j++) {
                colorLines.getField()[i][j] = 0;
            }
        }
        colorLines.getField()[1][0] = 0;
        colorLines.getField()[1][1] = 1;
        colorLines.getField()[1][2] = 1;
        colorLines.getField()[1][3] = 6;
        colorLines.getField()[1][4] = 6;
        colorLines.getField()[1][5] = 6;
        colorLines.getField()[1][6] = 6;
        colorLines.getField()[1][7] = 6;
        colorLines.getField()[1][8] = 2;
        assertEquals(10,colorLines.checkLines());
    }

    @Test
    public void delete_balls_diagonal_1(){
        ColorLines colorLines = new ColorLines(9);
        for (int i = 0; i < colorLines.getFieldSize(); i++) {
            for (int j = 0; j < colorLines.getFieldSize(); j++) {
                colorLines.getField()[i][j] = 0;
            }
        }
        colorLines.getField()[0][0] = 6;
        colorLines.getField()[1][1] = 6;
        colorLines.getField()[2][2] = 6;
        colorLines.getField()[3][3] = 6;
        colorLines.getField()[4][4] = 6;
        assertEquals(10,colorLines.checkLines());
    }

    @Test
    public void delete_balls_diagonal_2(){
        ColorLines colorLines = new ColorLines(9);
        for (int i = 0; i < colorLines.getFieldSize(); i++) {
            for (int j = 0; j < colorLines.getFieldSize(); j++) {
                colorLines.getField()[i][j] = 0;
            }
        }
        colorLines.getField()[4][4] = 6;
        colorLines.getField()[5][5] = 6;
        colorLines.getField()[6][6] = 6;
        colorLines.getField()[7][7] = 6;
        colorLines.getField()[8][8] = 6;
        assertEquals(10,colorLines.checkLines());
    }

    @Test
    public void delete_balls_diagonal_3(){
        ColorLines colorLines = new ColorLines(9);
        for (int i = 0; i < colorLines.getFieldSize(); i++) {
            for (int j = 0; j < colorLines.getFieldSize(); j++) {
                colorLines.getField()[i][j] = 0;
            }
        }
        colorLines.getField()[0][4] = 6;
        colorLines.getField()[1][5] = 6;
        colorLines.getField()[2][6] = 6;
        colorLines.getField()[3][7] = 6;
        colorLines.getField()[4][8] = 6;
        assertEquals(10,colorLines.checkLines());
    }

    @Test
    public void delete_balls_diagonal_4(){
        ColorLines colorLines = new ColorLines(9);
        for (int i = 0; i < colorLines.getFieldSize(); i++) {
            for (int j = 0; j < colorLines.getFieldSize(); j++) {
                colorLines.getField()[i][j] = 0;
            }
        }
        colorLines.getField()[4][0] = 6;
        colorLines.getField()[5][1] = 6;
        colorLines.getField()[6][2] = 6;
        colorLines.getField()[7][3] = 6;
        colorLines.getField()[8][4] = 6;
        assertEquals(10,colorLines.checkLines());
    }

    @Test
    public void delete_balls_diagonal_5(){
        ColorLines colorLines = new ColorLines(9);
        for (int i = 0; i < colorLines.getFieldSize(); i++) {
            for (int j = 0; j < colorLines.getFieldSize(); j++) {
                colorLines.getField()[i][j] = 0;
            }
        }
        colorLines.getField()[0][8] = 6;
        colorLines.getField()[1][7] = 6;
        colorLines.getField()[2][6] = 6;
        colorLines.getField()[3][5] = 6;
        colorLines.getField()[4][4] = 6;
        assertEquals(10,colorLines.checkLines());
    }

    @Test
    public void delete_balls_diagonal_6(){
        ColorLines colorLines = new ColorLines(9);
        for (int i = 0; i < colorLines.getFieldSize(); i++) {
            for (int j = 0; j < colorLines.getFieldSize(); j++) {
                colorLines.getField()[i][j] = 0;
            }
        }
        colorLines.getField()[8][0] = 6;
        colorLines.getField()[7][1] = 6;
        colorLines.getField()[6][2] = 6;
        colorLines.getField()[5][3] = 6;
        colorLines.getField()[4][4] = 6;
        assertEquals(10,colorLines.checkLines());
    }

    @Test
    public void delete_balls_diagonal_7(){
        ColorLines colorLines = new ColorLines(9);
        for (int i = 0; i < colorLines.getFieldSize(); i++) {
            for (int j = 0; j < colorLines.getFieldSize(); j++) {
                colorLines.getField()[i][j] = 0;
            }
        }
        colorLines.getField()[7][1] = 6;
        colorLines.getField()[6][2] = 6;
        colorLines.getField()[5][3] = 6;
        colorLines.getField()[4][4] = 6;
        colorLines.getField()[3][5] = 6;
        assertEquals(10,colorLines.checkLines());
    }

    @Test
    public void delete_balls_diagonal_8(){
        ColorLines colorLines = new ColorLines(9);
        for (int i = 0; i < colorLines.getFieldSize(); i++) {
            for (int j = 0; j < colorLines.getFieldSize(); j++) {
                colorLines.getField()[i][j] = 0;
            }
        }
        colorLines.getField()[0][0] = 6;
        colorLines.getField()[1][1] = 6;
        colorLines.getField()[2][2] = 6;
        colorLines.getField()[3][3] = 6;
        colorLines.getField()[4][4] = 6;
        colorLines.getField()[5][5] = 6;
        colorLines.getField()[6][6] = 6;
        colorLines.getField()[7][7] = 6;
        colorLines.getField()[8][8] = 6;
        assertEquals(18,colorLines.checkLines());
    }

    @Test
    public void delete_balls_diagonal_9(){
        ColorLines colorLines = new ColorLines(9);
        for (int i = 0; i < colorLines.getFieldSize(); i++) {
            for (int j = 0; j < colorLines.getFieldSize(); j++) {
                colorLines.getField()[i][j] = 0;
            }
        }
        colorLines.getField()[1][0] = 6;
        colorLines.getField()[2][1] = 6;
        colorLines.getField()[3][2] = 6;
        colorLines.getField()[4][3] = 6;
        colorLines.getField()[5][4] = 6;
        colorLines.getField()[6][5] = 6;
        colorLines.getField()[7][6] = 6;
        colorLines.getField()[8][7] = 6;
        assertEquals(16,colorLines.checkLines());
    }

    @Test
    public void delete_balls_diagonal_10(){
        ColorLines colorLines = new ColorLines(9);
        for (int i = 0; i < colorLines.getFieldSize(); i++) {
            for (int j = 0; j < colorLines.getFieldSize(); j++) {
                colorLines.getField()[i][j] = 0;
            }
        }
        colorLines.getField()[0][1] = 6;
        colorLines.getField()[1][2] = 6;
        colorLines.getField()[2][3] = 6;
        colorLines.getField()[3][4] = 6;
        colorLines.getField()[4][5] = 6;
        colorLines.getField()[5][6] = 6;
        colorLines.getField()[6][7] = 6;
        colorLines.getField()[7][8] = 6;
        assertEquals(16,colorLines.checkLines());
    }

    @Test
    public void delete_balls_diagonal_11(){
        ColorLines colorLines = new ColorLines(9);
        for (int i = 0; i < colorLines.getFieldSize(); i++) {
            for (int j = 0; j < colorLines.getFieldSize(); j++) {
                colorLines.getField()[i][j] = 0;
            }
        }
        colorLines.getField()[0][1] = 2;
        colorLines.getField()[1][2] = 2;
        colorLines.getField()[2][3] = 6;
        colorLines.getField()[3][4] = 6;
        colorLines.getField()[4][5] = 6;
        colorLines.getField()[5][6] = 6;
        colorLines.getField()[6][7] = 6;
        colorLines.getField()[7][8] = 1;
        assertEquals(10,colorLines.checkLines());
    }

    @Test
    public void delete_balls_diagonal_12(){
        ColorLines colorLines = new ColorLines(9);
        for (int i = 0; i < colorLines.getFieldSize(); i++) {
            for (int j = 0; j < colorLines.getFieldSize(); j++) {
                colorLines.getField()[i][j] = 0;
            }
        }
        colorLines.getField()[0][0] = 0;
        colorLines.getField()[1][1] = 2;
        colorLines.getField()[2][2] = 2;
        colorLines.getField()[3][3] = 6;
        colorLines.getField()[4][4] = 6;
        colorLines.getField()[5][5] = 6;
        colorLines.getField()[6][6] = 6;
        colorLines.getField()[7][7] = 6;
        colorLines.getField()[8][8] = 1;
        assertEquals(10,colorLines.checkLines());
    }
}