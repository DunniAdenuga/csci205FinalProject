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

import static SolvingAlgorithms.BacktrackAlgorithm.printGrid;
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
        int grid[][] = {{3, 0, 6, 5, 0, 8, 4, 0, 0},
                        {5, 2, 0, 0, 0, 0, 0, 0, 0},
                        {0, 8, 7, 0, 0, 0, 0, 3, 1},
                        {0, 0, 3, 0, 1, 0, 0, 8, 0},
                        {9, 0, 0, 8, 6, 3, 0, 0, 5},
                        {0, 5, 0, 0, 9, 0, 6, 0, 0},
                        {1, 3, 0, 0, 0, 0, 2, 5, 0},
                        {0, 0, 0, 0, 0, 0, 0, 7, 4},
                        {0, 0, 5, 2, 0, 6, 3, 0, 0}};

        Board board = new Board(grid);

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
        try {
            printGrid(newBoard.getIntGrid());
        } catch (Exception e) {
            printGrid(newBoard.getIntGrid());
        }

    }

}
