/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Dunni Adenuga
 Tim Woodford
 Andrew Nyhus
 * Date: Nov 16, 2015
 * Time: 10:29:46 AM
 *
 * Project: csci205FinalProject
 * Package: SudokuBoardGenerator
 * File: SudokuBoardGenerator
 * Description:
 *
 * ****************************************
 */
package SudokuBoardGenerator;

import SolvingAlgorithms.BacktrackAlgorithm;
import java.util.ArrayList;
import java.util.Random;
import sudoku.Board;

/**
 *
 * @author ia005
 */
/*
 *https://arunabhghosal.wordpress.com/2015/04/26/generating-sudoku-puzzle/
 *http://blog.forret.com/2006/08/a-sudoku-challenge-generator/
 */
public class SudokuBoardGenerator {

    Random random = new Random();
    int[][] intGrid = new int[9][9];
    ArrayList<Integer> possibleAnswers = new ArrayList<>();
    int type;
    int easy = 49;//type 0
    int medium = 56;// type 1
    int difficult = 61;// type 2

    int[][] grid1 = {{3, 1, 6, 5, 7, 8, 4, 9, 2},
                     {5, 2, 9, 1, 3, 4, 7, 6, 8},
                     {4, 8, 7, 6, 2, 9, 5, 3, 1},
                     {2, 6, 3, 4, 1, 5, 9, 8, 7},
                     {9, 7, 4, 8, 6, 3, 1, 2, 5},
                     {8, 5, 1, 7, 9, 2, 6, 4, 3},
                     {1, 3, 8, 9, 4, 7, 2, 5, 6},
                     {6, 9, 2, 3, 5, 1, 8, 7, 4},
                     {7, 4, 5, 2, 8, 6, 3, 1, 9}};

    int[][] grid2 = {{8, 3, 5, 4, 1, 6, 9, 2, 7},
                     {2, 9, 6, 8, 5, 7, 4, 3, 1},
                     {4, 1, 7, 2, 9, 3, 6, 5, 8},
                     {5, 6, 9, 1, 3, 4, 7, 8, 2},
                     {1, 2, 3, 6, 7, 8, 5, 4, 9},
                     {7, 4, 8, 5, 2, 9, 1, 6, 3},
                     {6, 5, 2, 7, 8, 1, 3, 9, 4},
                     {9, 8, 1, 3, 4, 5, 2, 7, 6},
                     {3, 7, 4, 9, 6, 2, 8, 1, 5}};

    int[][] grid3 = {{1, 2, 3, 4, 5, 6, 7, 8, 9},
                     {4, 5, 6, 7, 8, 9, 1, 2, 3},
                     {7, 8, 9, 1, 2, 3, 4, 5, 6},
                     {2, 3, 4, 5, 6, 7, 8, 9, 1},
                     {5, 6, 7, 8, 9, 1, 2, 3, 4},
                     {8, 9, 1, 2, 3, 4, 5, 6, 7},
                     {3, 4, 5, 6, 7, 8, 9, 1, 2},
                     {6, 7, 8, 9, 1, 2, 3, 4, 5},
                     {9, 1, 2, 3, 4, 5, 6, 7, 8}};
    int[][][] solvedBoards = {grid1, grid2, grid3};

    public SudokuBoardGenerator(int type) {
        this.type = type;

    }

    /**
     * randomly select solved board to use
     */
    public void chooseGrid() {
        intGrid = solvedBoards[random.nextInt(solvedBoards.length)].clone();
    }

    public void groupSwapRowCol() {
        int num1, num2;

        int min = 0, max = 2;
        for (int i = 0; i < 3; i++) {//there are just 3 groups
            num1 = random.nextInt((max - min) + 1) + min;
            do {
                num2 = random.nextInt((max - min) + 1) + min;
            } while (num1 == num2);
            int n = random.nextInt(2);
            if (n == 0) {
                swapRow(num1, num2);
            }
            if (n == 1) {
                swapCol(num1, num2);
            }
            min = min + 3;
            max = max + 3;
        }
    }

    public void wholeGroupSwaps() {
        int num1 = 0, num2 = 0;
        int m = random.nextInt(3) + 1;//there are 3 groups(1,2, 3)first group to swap
        int n;
        do {
            n = random.nextInt(3) + 1;
        } while (m == n);
        if (m == 1) {
            num1 = 0;
        }
        if (m == 2) {
            num1 = 3;
        }
        if (m == 3) {
            num1 = 6;
        }
        if (n == 1) {
            num2 = 0;
        }
        if (n == 2) {
            num2 = 3;
        }
        if (n == 3) {
            num2 = 6;
        }
        int x = random.nextInt(2);//help if column or row group change
        if (x == 0) {
            for (int i = 0; i < 3; i++) {
                swapRow(num1, num2);
                num1++;
                num2++;
            }
        }
        if (x == 1) {
            for (int i = 0; i < 3; i++) {
                swapCol(num1, num2);
                num1++;
                num2++;
            }
        }
    }

    public void swapRow(int x, int y) {
        for (int i = 0; i < 9; i++) {
            int temp;
            temp = intGrid[x][i];
            intGrid[x][i] = intGrid[y][i];
            intGrid[y][i] = temp;
        }
    }

    public void swapCol(int x, int y) {
        for (int i = 0; i < 9; i++) {
            int temp;
            temp = intGrid[i][x];
            intGrid[i][x] = intGrid[i][y];
            intGrid[i][y] = temp;
        }
    }
    /*
     * @see http://introcs.cs.princeton.edu/java/14array/Transpose.java.html
     */

    public void transpose() {
        for (int i = 0; i < 9; i++) {
            for (int j = i + 1; j < 9; j++) {
                int temp = intGrid[i][j];
                intGrid[i][j] = intGrid[j][i];
                intGrid[j][i] = temp;
            }
        }
    }
    /*
     Difficulty level:
     Easy - remove about 46 to 51
     Medium - remove about 51 to 56
     Difficulty - remove about 56 to 61
     */

    public void strikeOutCells() {
        int count = 0;
        int i;
        int j;
        int[] test = new int[2];
        ArrayList<int[]> visitedIJ = new ArrayList<>();

        while ((count < easy)) {
            //for (int i = 0; i < 9; i++) {
            //  for (int j = 0; j < 9; j++) {
            //for (int x = 1; x <= 9; x++) {
            do {
                i = random.nextInt(9);
                j = random.nextInt(9);
                test[0] = i;
                test[1] = j;
            } while (visitedIJ.contains(test));
            if (checkRow(i, j) == true) {
                //System.out.println("here");
                intGrid[i][j] = 0;

                count++;
                //System.out.println(count);
            }
            int[] done = {i, j};
            visitedIJ.add(done);
            //System.out.println("possSolu- " + possSolu);
            //}
            //strike_out(i, j);
            // }
            //}
        }
        //System.out.println(count);
    }

    public boolean checkRow(int row, int col2) {

        for (int x = 1; x <= 9; x++) {
            possibleAnswers.add(x);
        }

        for (int col = 0; col < 9; col++) {
            if (col != col2) {
                possibleAnswers.remove((Integer) intGrid[row][col]);
            }
        }
        return checkCol(col2, row);

    }

    /**
     * Checks if num is an acceptable value for the given column
     */
    public boolean checkCol(int col, int row2) {

        for (int row = 0; row < 9; row++) {
            if (row != row2) {
                possibleAnswers.remove((Integer) intGrid[row][col]);
            }
        }
        return checkBox(row2, col);
    }

    /**
     * Checks if num is an acceptable value for the box around row and col
     */
    public boolean checkBox(int row2, int col2) {

        int row = (row2 / 3) * 3;
        int col = (col2 / 3) * 3;

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if ((row + r != row2) && (col + c != col2)) {
                    possibleAnswers.remove((Integer) intGrid[row + r][col + c]);
                }
            }
        }
        if ((possibleAnswers.size() == 1) || (possibleAnswers.size() == 2))// || (possibleAnswers.size() == 3))
        {

            //System.out.println("possibleAnsSize " + possibleAnswers);
            return true;
        } else {
            return false;
        }

    }

    public void doMain() {
        int temp = random.nextInt(6);
        chooseGrid();
        if (temp == 0) {
            groupSwapRowCol();
            wholeGroupSwaps();
            transpose();

        }
        if (temp == 1) {
            groupSwapRowCol();
            transpose();
            wholeGroupSwaps();
        }
        if (temp == 2) {
            transpose();
            groupSwapRowCol();
            wholeGroupSwaps();
        }
        if (temp == 3) {
            transpose();
            wholeGroupSwaps();
            groupSwapRowCol();
        }
        if (temp == 4) {
            wholeGroupSwaps();
            transpose();
            groupSwapRowCol();
        }
        if (temp == 5) {
            wholeGroupSwaps();
            groupSwapRowCol();
            transpose();
        }

        //Board.printGrid(intGrid);
        //System.out.println();
        if (type == 0) {
            eraser(easy);
        } else if (type == 1) {
            eraser(medium);
        } else if (type == 2) {
            eraser(difficult);
        } else {
            eraser(easy);//default
        }        //strikeOutCells();
    }

    public Board generateBoard() {
        doMain();
        return new Board(intGrid);
    }

    /*public void strike_out(int k1, int k2) {
     int row_from;
     int row_to;
     int col_from;
     int col_to;
     int i, j, b, c;
     int rem1, rem2;
     int flag;
     int temp = intGrid[k1][k2];
     int count = 9;
     for (i = 1; i <= 9; i++) {
     flag = 1;
     for (j = 0; j < 9; j++) {
     if (j != k2) {
     if (i != intGrid[k1][j]) {
     continue;
     } else {
     flag = 0;
     break;
     }
     }
     }
     if (flag == 1) {
     for (c = 0; c < 9; c++) {
     if (c != k1) {
     if (i != intGrid[c][k2]) {
     continue;
     } else {
     flag = 0;
     break;
     }
     }
     }
     }
     if (flag == 1) {
     rem1 = k1 % 3;
     rem2 = k2 % 3;
     row_from = k1 - rem1;
     row_to = k1 + (2 - rem1);
     col_from = k2 - rem2;
     col_to = k2 + (2 - rem2);
     for (c = row_from; c <= row_to; c++) {
     for (b = col_from; b <= col_to; b++) {
     if (c != k1 && b != k2) {
     if (i != intGrid[c][b]) {
     continue;
     } else {
     flag = 0;
     break;
     }
     }
     }
     }
     }
     if (flag == 0) {
     count--;
     }
     }
     if (count == 1) {
     intGrid[k1][k2] = 0;
     count--;
     }
     }*/
    public static void main(String[] args) {
        //SudokuBoardEasy easy = new SudokuBoardGenerator();
        //easy.doMain();
        //Board.printGrid(easy.intGrid);

        //System.out.println();
        SudokuBoardGenerator easy2 = new SudokuBoardGenerator(0);
        int[][] testGrid = easy2.generateBoard().getIntGrid();
        Board.printGrid(testGrid);
        //easy.generateBoard()
        BacktrackAlgorithm backtrack = new BacktrackAlgorithm();
        //System.out.println();
        Board newBoard2 = backtrack.solveBoard(new Board(testGrid));
        backtrack.printGrid(newBoard2.getIntGrid());

    }

    public void erase(int count) {
        int x = 1;
        int i;
        int j;
        int[] test = new int[2];
        ArrayList<int[]> visitedI = new ArrayList<>();
        //ArrayList<Integer> visitedJ = new ArrayList<>();
        while (x < count / 2) {

            x++;
            //do {
            i = random.nextInt(5);
            j = random.nextInt(9);
            test[0] = i;
            test[1] = j;
            // } while (checkList(visitedI, test));

            intGrid[i][j] = 0;

            visitedI.add(test);

        }
        while (x < count) {

            x++;
            //do {
            i = random.nextInt(4) + 5;
            j = random.nextInt(9);
            test[0] = i;
            test[1] = j;
            //} while (checkList(visitedI, test));

            intGrid[i][j] = 0;

            visitedI.add(test);

        }
        //System.out.println(visitedI);

    }

    public void eraser(int count) {
        int check = random.nextInt();
        int num = count / 9;

        int x = 1;
        if (check % 2 == 0) {
            for (int i = 0; i < 9; i++) {
                ArrayList<Integer> intA = new ArrayList<>();
                while (x < num + 1) {
                    x++;
                    int test;
                    do {
                        test = random.nextInt(9);
                    } while (intA.contains(test));
                    intGrid[i][test] = 0;
                    intA.add(test);
                }
                x = 1;

            }
        } else {
            for (int i = 0; i < 9; i++) {
                ArrayList<Integer> intA = new ArrayList<>();
                while (x < num + 1) {
                    x++;
                    int test;
                    do {
                        test = random.nextInt(9);
                    } while (intA.contains(test));
                    intGrid[test][i] = 0;
                    intA.add(test);
                }
                x = 1;

            }

        }
    }

    public boolean checkList(ArrayList<int[]> list, int[] test) {
        int i = 1;
        while (i < list.size()) {
            if ((list.get(i - 1)[0] == test[0]) && (list.get(i - 1)[1] == test[1])) {
                return true;

            }
            i++;
        }
        return false;
    }
}
