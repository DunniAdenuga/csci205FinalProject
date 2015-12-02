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
import sudoku.Square;

/**
 * Simulated annealing state for use in a sudoku solver
 *
 * @author Tim Woodford
 */
public class SASudokuState implements SAState {

    private static final Random rnd = new Random();
    private final Board board;

    public SASudokuState(Board board) {
        this.board = board;
    }

    /**
     * Evaluate the current solution by counting the number of non-present
     * numbers in each segment (row, col, square). Higher (less negative)
     * numbers are closer to being correct.
     *
     * @return The evaluation number
     */
    @Override
    public double evaluate() {
        int totalBad = 0;
        for (int i = 0; i < 9; i++) {
            totalBad += board.getRow(i).getNumNotPresent();
            totalBad += board.getCol(i).getNumNotPresent();
            //totalBad += board.getSquare(i).getNumNotPresent();
        }
        return -totalBad;
    }

    /**
     * Randomly swap two numbers on the board.
     *
     * @return A new board with two random editable numbers swapped
     */
    @Override
    public SAState randomize() {
        Board nboard = board.clone();
        Square sq = nboard.getSquare(rnd.nextInt(Board.BOARD_SIZE));
        int loc1 = getEditableIndex(sq);
        int loc2;
        do {
            loc2 = getEditableIndex(sq);
        } while (loc1 == loc2);
        CellValue val1 = sq.getValueAtIndex(loc1);
        CellValue val2 = sq.getValueAtIndex(loc2);
        sq.setValueAtIndex(loc2, val1);
        sq.setValueAtIndex(loc1, val2);
        //printGrid(board.getIntGrid());
        //System.out.println("New thing: " + evaluate());
        return new SASudokuState(nboard);
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

    /**
     * Get the current board state
     *
     * @return The current <code>Board</code>
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Fill editable spaces with random values such that each square has all
     * nine numbers
     */
    public void invalidFill() {
        for (int i = 0; i < Board.BOARD_SIZE; i++) {
            invalidFill(board.getSquare(i));
        }
    }

    /**
     * Fill editable spaces with random values such that all values are present
     *
     * @param sq The square to fill
     */
    private void invalidFill(Square sq) {
        boolean[] valueCounts = new boolean[9];
        for (int i = 0; i < Board.BOARD_SIZE; i++) {
            if (!sq.getEditabilityAtIndex(i)) {
                valueCounts[sq.getValueAtIndex(i).getValue() - 1] = true;
            }
        }
        for (int i = 0; i < Board.BOARD_SIZE; i++) {
            if (!valueCounts[i]) {
                int index = getEditableEmptyIndex(sq);
                sq.setValueAtIndex(index, board.createCellValueFromInt(i + 1));
            }
        }
    }

    /**
     * Randomly pick an empty, editable index
     *
     * @param sq The square to pick from
     * @return An empty, editable index
     */
    private int getEditableEmptyIndex(Square sq) {
        int index = -1;
        do {
            index = rnd.nextInt(Board.BOARD_SIZE);
        } while (!sq.getEditabilityAtIndex(index) || sq.getValueAtIndex(
                index) != CellValue.EMPTY);
        return index;
    }

    /**
     * Randomly pick an editable index
     *
     * @param sq The square to pick from
     * @return An editable index
     */
    private int getEditableIndex(Square sq) {
        int index = -1;
        do {
            index = rnd.nextInt(Board.BOARD_SIZE);
        } while (!sq.getEditabilityAtIndex(index));
        return index;
    }

    private int getAvailableIndex(Square sq) {
        int index = -1;
        Location loc = null;
        do {
            index = getEditableIndex(sq);
            loc = sq.getLocationInSquare(index);
        } while (board.getRow(loc.getY())
                .isCompleted() && board.getCol(loc.
                        getX()).isCompleted());
        return index;
    }
}
