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

import java.util.Random;
import sudoku.Board;
import sudoku.CellValue;
import sudoku.Location;
import sudoku.solvers.SudokuSolver;

/**
 * Simulated annealing-based solver
 *
 * @author Tim Woodford
 */
public class SASolver implements SudokuSolver {

    private Random rnd = new Random();

    @Override
    public Board solveBoard(Board input) {
        Board copy = input.clone();
        invalidFill(copy);
        SASudokuState state = new SASudokuState(copy);
        SimpleAnnealer<SASudokuState> annealer = new SimpleAnnealer<>(10, 1, 0.1);
        SASudokuState result = annealer.anneal(state);
        // TODO check for completion, reheat if needed
        return result.getBoard();
    }

    private void invalidFill(Board board) {
        int[] valueCounts = new int[9];
        // Count currently filled values
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Location loc = new Location(i, j);
                CellValue val = board.getValueAtLoc(loc);
                if (!board.getEditabilityAtLoc(loc)) {
                    valueCounts[val.getValue() - 1]++;
                }
            }
        }

        // Put in new values
        int currNum = 0, fillCount = 8;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Location loc = new Location(i, j);
                if (board.getEditabilityAtLoc(loc)) {
                    board.setValueAtLoc(loc, board.
                                        createCellValueFromInt(currNum + 1));
                    fillCount--;
                    if (fillCount < valueCounts[currNum]) {
                        fillCount = 8;
                        currNum++;
                    }
                }
            }
        }
    }
}
