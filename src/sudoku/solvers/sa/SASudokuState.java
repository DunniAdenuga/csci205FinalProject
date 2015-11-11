/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Dunni Adenuga
 Tim Woodford
 Andrew Nyhus
 * Date: Nov 10, 2015
 * Time: 11:59:58 PM
 *
 * Project: csci205FinalProject
 * Package: sudoku.solvers.sa
 * File: SASudokuState
 * Description:
 *
 * ****************************************
 */
package sudoku.solvers.sa;

import java.util.Random;
import sudoku.Board;
import sudoku.CellValue;
import sudoku.Location;

/**
 * Simulated annealing state for use in a sudoku solver
 *
 * @author Tim Woodford
 */
public class SASudokuState implements SAState {

    private static final Random rnd = new Random();
    private Board board;

    public SASudokuState(Board board) {
        this.board = board;
    }

    @Override
    public double evaluate() {
        // Can't be implemented without some changes to Board
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Randomly swap two numbers on the board.
     *
     * @return A new board with two random editable numbers swapped
     */
    @Override
    public SAState randomize() {
        Location loc1 = rndLoc();
        Location loc2;
        do {
            loc2 = rndLoc();
        } while (loc1.equals(loc2));
        try {
            Board nboard = board.clone();
            CellValue val1 = nboard.getValueAtLoc(loc1);
            CellValue val2 = nboard.getValueAtLoc(loc2);
            nboard.setValueAtLoc(loc2, val1);
            nboard.setValueAtLoc(loc1, val2);
            return new SASudokuState(nboard);
        } catch (CloneNotSupportedException ex) {
            return null;
        }
    }

    /**
     * Pick a random editable location
     *
     * @return A random, editable location
     */
    private Location rndLoc() {
        Location loc;
        do {
            loc = new Location(rnd.nextInt(9), rnd.nextInt(9));
        } while (!board.getEditabilityAtLoc(loc));
        return loc;
    }
}
