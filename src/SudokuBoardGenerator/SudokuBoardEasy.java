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
 * File: SudokuBoardEasy
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
public class SudokuBoardEasy implements SudokuBoard {

    Random random = new Random();
    int[][] intGrid = new int[9][9];
    ArrayList<Integer> possibleAnswers = new ArrayList<>();
    int easy = 51;
    int medium = 56;
    int difficult = 61;

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

        //while (count < easy) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                //for (int x = 1; x <= 9; x++) {
                if (checkRow(i, j) == true) {
                    //System.out.println("here");
                    intGrid[i][j] = 0;
                    count++;

                }
                //System.out.println("possSolu- " + possSolu);
                //}

            }
        }

        //}
        System.out.println(count);
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
        if ((possibleAnswers.size() == 1) || (possibleAnswers.size() == 2)) {
            //System.out.println("possibleAnsSize " + possibleAnswers);
            return true;
        } else {
            return false;
        }

    }

    public void doMain() {
        chooseGrid();
        groupSwapRowCol();
        wholeGroupSwaps();
        transpose();
        Board.printGrid(intGrid);
        strikeOutCells();
    }

    @Override
    public Board generateBoard() {
        doMain();
        return new Board(intGrid);
    }

    public static void main(String[] args) {
        //SudokuBoardEasy easy = new SudokuBoardEasy();
        //easy.doMain();
        //Board.printGrid(easy.intGrid);

        System.out.println();
        SudokuBoardEasy easy2 = new SudokuBoardEasy();
        int[][] testGrid = easy2.generateBoard().getIntGrid();
        Board.printGrid(testGrid);
        //easy.generateBoard()
        BacktrackAlgorithm backtrack = new BacktrackAlgorithm();
        System.out.println();
        Board newBoard2 = backtrack.solveBoard(new Board(testGrid));
        backtrack.printGrid(newBoard2.getIntGrid());

    }
}
