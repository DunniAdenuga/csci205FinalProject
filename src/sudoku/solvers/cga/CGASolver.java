/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Dunni Adenuga
 Tim Woodford
 Andrew Nyhus
 * Date: Nov 29, 2015
 * Time: 11:05:59 PM
 *
 * Project: csci205FinalProject
 * Package: sudoku.solvers.cga
 * File: CGASolver
 * Description:
 *
 * ****************************************
 */
package sudoku.solvers.cga;

import sudoku.Board;
import sudoku.solvers.SudokuSolver;

/**
 * A solver that uses a cultural genetic algorithm for determining the correct
 * values
 *
 * @author Tim Woodford
 */
public class CGASolver implements SudokuSolver {

    @Override
    public Board solveBoard(Board input) {
        CGASudokuState state = new CGASudokuState(input);
        CGAEvolver evolver = new CGAEvolver();
        return ((CGASudokuState) evolver.evolve(state, 50)).getBoard();
    }

}
