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
 * This is a brute-force solution to solving a sudoku board It uses trial and
 * error methods through recursion
 *
 * @author Dunni Adenuga
 *
 * Adapted from:
 * @see http://www.geeksforgeeks.org/backtracking-set-7-suduku/
 */
public class BacktrackAlgorithm implements SudokuSolver {

    private Board newBoard;
    private Board oldBoard;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*  test  */
        // 0 means empty
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

        BacktrackAlgorithm backtrack = new BacktrackAlgorithm(grid);

        backtrack.printGrid(newBoard1.getIntGrid());

        System.out.println();
        Board newBoard2 = backtrack.solveBoard(newBoard1);
        backtrack.printGrid(newBoard2.getIntGrid());

    }

    public BacktrackAlgorithm() {

    }

    public BacktrackAlgorithm(Board board) {
        newBoard = board.clone();
        oldBoard = board.clone();

    }

    public BacktrackAlgorithm(int[][] board) {
        newBoard = new Board(board);
        oldBoard = new Board(board);

    }

    /**
     * A utility function to print grid
     *
     * @param grid - board to be printed
     */
    public static void printGrid(int grid[][]) {
        for (int row1 = 0; row1 < grid.length; row1++) {
            for (int col1 = 0; col1 < grid.length; col1++) {
                System.out.printf("%2d", grid[row1][col1]);
            }
            System.out.printf("\n");
        }
    }

    /**
     * Recursive function that tests out safe numbers in a given cell to see if
     * it gives a solution
     *
     * @param row - helps locate cell
     * @param col - helps locate cell
     * @throws Exception - signals final result has been found
     * @see http://www.heimetli.ch/ffh/simplifiedsudoku.html
     */
    public void solve(int row, int col) throws Exception {

        // Throw an exception to stop the process if the puzzle is solved
        if (row > 8) {

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
     * Sets up the recursion by calling solve() for the next cell row & col -
     * indices
     *
     * @param row - current row
     * @param col - current cell
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
     *
     * @param row - row to be checked
     * @param num - see if num is safe in row
     * @return boolean -true or false
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
     *
     * @param col - col to be checked
     * @param num - see if num is safe in col
     * @return boolean -true or false
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
     * Checks if num is an acceptable value for the given cell's 3 * 3 box
     *
     * @param col - current column
     * @param num - current row
     * @return boolean -true or false
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

    /**
     * Solve board and return solution
     *
     * @param input - board to be solved
     * @return solution
     */
    @Override
    public Board solveBoard(Board input) {
        try {
            newBoard = input.clone();
            oldBoard = input.clone();
            //newBoard = new Board(input.getIntGrid());
            solve(0, 0);

        } catch (Exception ex) {
            return newBoard;
        }
        return newBoard;
    }

    /**
     * Solve board and return solution
     *
     * @param input - board to be solved as int[][]
     * @return solution as Board class
     */
    public Board solveBoard(int[][] input) {
        try {
            //newBoard = input.clone();
            newBoard = new Board(input);
            oldBoard = new Board(input);
            solve(0, 0);

        } catch (Exception ex) {
            if (newBoard.equals(oldBoard)) {
                return null;
            } else {
                return newBoard;
            }
        }
        return null;
    }
}
