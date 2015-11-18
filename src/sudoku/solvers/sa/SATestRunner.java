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
        int grid[][] = {{0, 2, 4, 0, 0, 7, 0, 0, 0},
                        {6, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 3, 6, 8, 0, 4, 1, 5},
                        {4, 3, 1, 0, 0, 5, 0, 0, 0},
                        {5, 0, 0, 0, 0, 0, 0, 3, 2},
                        {7, 9, 0, 0, 0, 0, 0, 6, 0},
                        {2, 0, 9, 7, 1, 0, 8, 0, 0},
                        {0, 4, 0, 0, 9, 3, 0, 0, 0},
                        {3, 1, 0, 0, 0, 4, 7, 5, 0}};

        Board board = new Board(grid);
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

        SudokuSolver solver = new SASolver();
        Board newBoard = solver.solveBoard(board);
        printGrid(newBoard.getIntGrid());
        int row = 0, col = 0;
        for (int i = 0; i < 9; i++) {
            col += newBoard.getCol(i).getNumNotPresent();
            row += newBoard.getRow(i).getNumNotPresent();
            System.out.println("Col: " + newBoard.getCol(i).getNumNotPresent());
        }
        System.out.println("Row anomalies: " + row);
        System.out.println("Column anomalies: " + col);
    }

    public static void printGrid(int grid[][]) {
        for (int row1 = 0; row1 < 9; row1++) {
            for (int col1 = 0; col1 < 9; col1++) {
                System.out.printf("%2d", grid[col1][row1]);
            }
            System.out.printf("\n");
        }
    }
}
