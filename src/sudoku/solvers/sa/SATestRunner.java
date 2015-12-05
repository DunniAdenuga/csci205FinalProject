/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Dunni Adenuga
 Tim Woodford
 Andrew Nyhus
 * Date: Nov 16, 2015
 * Time: 12:21:08 AM
 *
 * Project: csci205FinalProject
 * Package: sudoku.solvers.sa
 * File: SATestRunner
 * Description:
 *
 * ****************************************
 */
package sudoku.solvers.sa;

import sudoku.Board;
import sudoku.Location;
import sudoku.solvers.DeterministicSquareFinder;
import sudoku.solvers.SudokuSolver;

/**
 *
 * @author timothy
 */
public class SATestRunner {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /* Driver Program to test above functions */
        // 0 means unassigned cells
        /*int grid[][] = {{0, 2, 4, 0, 0, 7, 0, 0, 0},
         {6, 0, 0, 0, 0, 0, 0, 0, 0},
         {0, 0, 3, 6, 8, 0, 4, 1, 5},
         {4, 3, 1, 0, 0, 5, 0, 0, 0},
         {5, 0, 0, 0, 0, 0, 0, 3, 2},
         {7, 9, 0, 0, 0, 0, 0, 6, 0},
         {2, 0, 9, 7, 1, 0, 8, 0, 0},
         {0, 4, 0, 0, 9, 3, 0, 0, 0},
         {3, 1, 0, 0, 0, 4, 7, 5, 0}};*/
        /*int grid[][] = {{3, 0, 6, 5, 0, 8, 4, 0, 0},
         {5, 2, 0, 0, 0, 0, 0, 0, 0},
         {0, 8, 7, 0, 0, 0, 0, 3, 1},
         {0, 0, 3, 0, 1, 0, 0, 8, 0},
         {9, 0, 0, 8, 6, 3, 0, 0, 5},
         {0, 5, 0, 0, 9, 0, 6, 0, 0},
         {1, 3, 0, 0, 0, 0, 2, 5, 0},
         {0, 0, 0, 0, 0, 0, 0, 7, 4},
         {0, 0, 5, 2, 0, 6, 3, 0, 0}};*/
        /*int grid[][] = {{6, 0, 2, 0, 4, 1, 0, 0, 5},
         {1, 0, 0, 0, 0, 5, 8, 4, 0},
         {8, 5, 4, 0, 0, 7, 0, 9, 1},
         {3, 0, 0, 4, 0, 2, 5, 7, 0},
         {4, 0, 8, 7, 5, 0, 1, 0, 9},
         {5, 0, 6, 0, 1, 0, 0, 2, 0},
         {0, 0, 0, 1, 2, 0, 0, 0, 7},
         {7, 4, 0, 0, 0, 0, 0, 1, 2},
         {2, 0, 1, 0, 0, 4, 9, 0, 3}};*/
        int grid[][] = {{6, 9, 2, 0, 4, 1, 0, 0, 5},
                        {1, 3, 0, 0, 0, 5, 8, 4, 6},
                        {8, 5, 4, 0, 0, 7, 0, 9, 1},
                        {3, 1, 0, 4, 6, 2, 5, 7, 8},
                        {4, 2, 8, 7, 5, 0, 1, 6, 9},
                        {5, 7, 6, 0, 1, 0, 0, 2, 4},
                        {0, 0, 0, 1, 2, 6, 0, 5, 7},
                        {7, 4, 0, 0, 0, 0, 0, 1, 2},
                        {2, 6, 1, 0, 0, 4, 9, 0, 3}};

        Board board = new Board(grid);

        System.out.println(board.getValueAtLoc(new Location(0, 4)));
        System.out.println(DeterministicSquareFinder.determineSquare(board,
                                                                     new Location(
                                                                             0,
                                                                             4)));
        printGrid(board.getIntGrid());

        // TODO put this into a central location
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                Location curr = new Location(x, y);
                if (board.getValueAtLoc(curr).isEmpty()) {
                    board.setEditabilityAtLoc(curr, true);
                }
            }
        }

        evaluateResult(board);

        Board det = board.clone();
        for (int i = 0; i < 2; i++) {
            DeterministicSquareFinder.determineSquares(det);
        }

        printGrid(det.getIntGrid());
        evaluateResult(det);

        SudokuSolver solver = new SASolver();
        Board newBoard = solver.solveBoard(board);

        printGrid(newBoard.getIntGrid());
        evaluateResult(newBoard);
    }

    private static void evaluateResult(Board newBoard) {
        int row = 0, col = 0, rowi = 0, coli = 0, sq = 0;
        for (int i = 0; i < 9; i++) {
            col += newBoard.getCol(i).getNumNotPresent();
            row += newBoard.getRow(i).getNumNotPresent();
            coli += newBoard.getCol(i).isValid() ? 0 : 1;
            rowi += newBoard.getRow(i).isValid() ? 0 : 1;
            sq += newBoard.getSquare(i).isValid() ? 0 : 1;
            System.out.println("Row: " + newBoard.getRow(i).getNumNotPresent());
        }
        System.out.println("Row anomalies: " + row);
        System.out.println("Column anomalies: " + col);
        System.out.println("Rows inconsistent: " + rowi);
        System.out.println("Columns inconsistent: " + coli);
        System.out.println("Squares inconsistent: " + sq);
    }

    public static void printGrid(int grid[][]) {
        for (int row1 = 0; row1 < 9; row1++) {
            for (int col1 = 0; col1 < 9; col1++) {
                System.out.printf("%2d", grid[row1][col1]);
            }
            System.out.printf("\n");
        }
    }
}
