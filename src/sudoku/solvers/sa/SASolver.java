/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Dunni Adenuga
 Tim Woodford
 Andrew Nyhus
 * Date: Nov 9, 2015
 * Time: 8:38:59 AM
 *
 * Project: csci205FinalProject
 * Package: sudoku.solvers
 * File: SASolver
 * Description:
 *
 * ****************************************
 */
package sudoku.solvers.sa;

import sudoku.Board;
import sudoku.solvers.SudokuSolver;

/**
 * Simulated annealing-based solver
 *
 * @author Tim Woodford
 */
public class SASolver implements SudokuSolver {

    @Override
    public Board solveBoard(Board input) {
        Board copy = input.clone();
        SASudokuState state = new SASudokuState(copy);
        state.invalidFill();
        SimpleAnnealer<SASudokuState> annealer = new SimpleAnnealer<>(1, 0.001,
                                                                      0.9999);
        SASudokuState result = annealer.anneal(state);
        // TODO check for completion, reheat if needed
        return result.getBoard();
    }
}
