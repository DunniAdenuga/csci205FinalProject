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
import sudoku.CellValue;
import sudoku.Location;
import sudoku.solvers.SudokuSolver;

/**
 *
 * @author ia005
 * @see http://www.geeksforgeeks.org/backtracking-set-7-suduku/
 */
public class BacktrackAlgorithm implements SudokuSolver {
    // N is used for size of Sudoku grid. Size will be NxN
    //private static final int N = 9;
    // UNASSIGNED is used for empty cells in sudoku grid
    //private static final int UNASSIGNED = 0;
    //private static int row = 0;
    //private static int col = 0;
    //private static int[][] model = new int[N][N];
    private Board newBoard;

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

        Board newBoard1 = new Board(grid);
        //backtrack.newBoard = new Board(grid);
        BacktrackAlgorithm backtrack = new BacktrackAlgorithm(grid);
        //backtrack.newBoard = new Board(grid);
        backtrack.printGrid(newBoard1.getIntGrid());
        //if (SolveSudoku(grid) == true) {
        //model = grid;
        //SolveSudoku(grid);

        /* try {
         backtrack.printGrid(backtrack.newBoard.getIntGrid());
         backtrack.solve(0, 0);
         } catch (Exception e) {
         System.out.println();
         backtrack.printGrid(backtrack.newBoard.getIntGrid());
         }*/
        System.out.println();
        Board newBoard2 = backtrack.solveBoard(newBoard1);
        backtrack.printGrid(newBoard2.getIntGrid());

    }

    public BacktrackAlgorithm(Board board) {
        newBoard = board.clone();

    }

    public BacktrackAlgorithm(int[][] board) {
        newBoard = new Board(board);

    }

    /* A utility function to print grid  */
    public void printGrid(int grid[][]) {
        for (int row1 = 0; row1 < grid.length; row1++) {
            for (int col1 = 0; col1 < grid.length; col1++) {
                System.out.printf("%2d", grid[row1][col1]);
            }
            System.out.printf("\n");
        }
    }

    /**
     * @return @see http://www.heimetli.ch/ffh/simplifiedsudoku.html
     * @param row
     * @param col
     * @throws Exception
     */
    public void solve(int row, int col) throws Exception {

        // Throw an exception to stop the process if the puzzle is solved
        if (row > 8) {
            //printGrid(newBoard.getIntGrid());
            throw new Exception("done");
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
                    newBoard.setValueAtLoc(new Location(row, col),
                                           CellValue.createCellValueFromInt(num));

                    // Delegate work on the next cell to a recursive call
                    next(row, col);
                }
            }

            // No valid number was found, clean up and return to caller
            newBoard.setValueAtLoc(new Location(row, col), CellValue.EMPTY);

        }

    }

    /**
     * Calls solve for the next cell row & col - indices
     */
    public void next(int row, int col) throws Exception {
        if (col < 8) {
            solve(row, col + 1);
        } else {
            solve(row + 1, 0);
        }
    }

    /**
     * Checks if num is an acceptable value for the given row
     */
    public boolean checkRow(int row, int num) {
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
    public boolean checkCol(int col, int num) {
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
    public boolean checkBox(int row, int col, int num) {
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

    @Override
    public Board solveBoard(Board input) {
        try {
            newBoard = input.clone();
            //newBoard = new Board(input.getIntGrid());
            solve(0, 0);

        } catch (Exception ex) {
            return newBoard;
        }
        return newBoard;
    }

    public Board solveBoard(int[][] input) {
        try {
            //newBoard = input.clone();
            newBoard = new Board(input);
            solve(0, 0);

        } catch (Exception ex) {
            return newBoard;
        }
        return newBoard;
    }

}
