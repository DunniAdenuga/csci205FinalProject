/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Dunni Adenuga
 Tim Woodford
 Andrew Nyhus
 * Date: Nov 10, 2015
 * Time: 11:27:41 PM
 *
 * Project: csci205FinalProject
 * Package: sudoku.solvers
 * File: SudokuSolver
 * Description:
 *
 * ****************************************
 */
package sudoku.solvers;

import sudoku.Board;

/**
 * A method for solving a Sudoku puzzle.
 *
 * @author Tim Woodford
 */
public interface SudokuSolver {

    /**
     * Find a valid set of numbers to fill in the blanks on the board.
     *
     * @param input The board with blanks, with fixed numbers properly
     * designated
     * @return A filled-in board
     */
    Board solveBoard(Board input);
}
