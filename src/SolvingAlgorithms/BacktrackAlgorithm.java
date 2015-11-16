/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Dunni Adenuga
 Tim Woodford
 Andrew Nyhus
 * Date: Nov 8, 2015
 * Time: 6:19:52 PM
 *
 * Project: csci205FinalProject
 * Package: SolvingAlgorithms
 * File: BacktrackAlgorithm
 * Description:
 *
 * ****************************************
 */
package SolvingAlgorithms;

import sudoku.Board;

/**
 *
 * @author ia005
 * @see http://www.geeksforgeeks.org/backtracking-set-7-suduku/
 */
public class BacktrackAlgorithm {
    // N is used for size of Sudoku grid. Size will be NxN
    private static final int N = 9;
    // UNASSIGNED is used for empty cells in sudoku grid
    //private static final int UNASSIGNED = 0;
    //private static int row = 0;
    //private static int col = 0;
    //private static int[][] model = new int[N][N];
    private static Board newBoard;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /* Driver Program to test above functions */
        // 0 means unassigned cells
        int grid[][] = {{3, 0, 6, 5, 0, 8, 4, 0, 0},
                        {5, 2, 0, 0, 0, 0, 0, 0, 0},
                        {0, 8, 7, 0, 0, 0, 0, 3, 1},
                        {0, 0, 3, 0, 1, 0, 0, 8, 0},
                        {9, 0, 0, 8, 6, 3, 0, 0, 5},
                        {0, 5, 0, 0, 9, 0, 6, 0, 0},
                        {1, 3, 0, 0, 0, 0, 2, 5, 0},
                        {0, 0, 0, 0, 0, 0, 0, 7, 4},
                        {0, 0, 5, 2, 0, 6, 3, 0, 0}};
        
        newBoard = new Board(grid);
        //if (SolveSudoku(grid) == true) {
        //model = grid;
        //SolveSudoku(grid);
        try {
            printGrid(newBoard.getIntGrid());
            solve(0, 0);
        } catch (Exception e) {
            printGrid(newBoard.getIntGrid());
        }

    }

    /* A utility function to print grid  */
    public static void printGrid(int grid[][]) {
        for (int row1 = 0; row1 < N; row1++) {
            for (int col1 = 0; col1 < N; col1++) {
                System.out.printf("%2d", grid[row1][col1]);
            }
            System.out.printf("\n");
        }
    }

    /**
     * @see http://www.heimetli.ch/ffh/simplifiedsudoku.html
     * @param row
     * @param col
     * @throws Exception
     */
    public static void solve(int row, int col) throws Exception {
        // Throw an exception to stop the process if the puzzle is solved
        if (row > 8) {
            throw new Exception("Solution found");
        }

        // If the cell is not empty, continue with the next cell
        if (newBoard.getIntGrid()[row][col] != 0) {
            next(row, col);
        } else {
            // Find a valid number for the empty cell
            for (int num = 1; num < 10; num++) {
                if (checkRow(row, num) && checkCol(col, num) && checkBox(row,
                                                                         col,
                                                                         num)) {
                    newBoard.getIntGrid()[row][col] = num;

                    // Delegate work on the next cell to a recursive call
                    next(row, col);
                }
            }

            // No valid number was found, clean up and return to caller
            newBoard.getIntGrid()[row][col] = 0;

        }
    }

    /**
     * Calls solve for the next cell row & col - indices
     */
    public static void next(int row, int col) throws Exception {
        if (col < 8) {
            solve(row, col + 1);
        } else {
            solve(row + 1, 0);
        }
    }

    /**
     * Checks if num is an acceptable value for the given row
     */
    protected static boolean checkRow(int row, int num) {
        for (int col = 0; col < 9; col++) {
            if (newBoard.getIntGrid()[row][col] == num) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks if num is an acceptable value for the given column
     */
    protected static boolean checkCol(int col, int num) {
        for (int row = 0; row < 9; row++) {
            if (newBoard.getIntGrid()[row][col] == num) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks if num is an acceptable value for the box around row and col
     */
    protected static boolean checkBox(int row, int col, int num) {
        row = (row / 3) * 3;
        col = (col / 3) * 3;

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (newBoard.getIntGrid()[row + r][col + c] == num) {
                    return false;
                }
            }
        }

        return true;
    }

}
