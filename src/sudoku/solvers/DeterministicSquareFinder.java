/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Dunni Adenuga
 Tim Woodford
 Andrew Nyhus
 * Date: Nov 28, 2015
 * Time: 12:09:07 PM
 *
 * Project: csci205FinalProject
 * Package: sudoku.solvers
 * File: DeterministicSquareFinder
 * Description:
 *
 * ****************************************
 */
package sudoku.solvers;

import java.util.ArrayList;
import java.util.List;
import sudoku.Board;
import sudoku.BoardSegment;
import sudoku.CellValue;
import sudoku.Location;

/**
 * Determines squares that must have a certain value due to the constraints of
 * Sudoku. Contains a static method that can be used to do this and an instance
 * of the object can be used as a decorator to an existing solve method.
 *
 * @author Tim Woodford
 */
public class DeterministicSquareFinder implements SudokuSolver {

    private final SudokuSolver innerSolver;

    /**
     * Create a decorator for a solver that finds all known squares before
     * sending it to the solver
     *
     * @param innerSolver The solver to use for the non-deterministic squares
     */
    public DeterministicSquareFinder(SudokuSolver innerSolver) {
        this.innerSolver = innerSolver;
    }

    @Override
    public Board solveBoard(Board input) {
        Board det = null;
        Board ndet = input.clone();
        while (!ndet.equals(det)) {
            det = ndet;
            ndet = det.clone();
            determineSquares(ndet, true);
        }
        if (ndet.isCompleted()) {
            return ndet;
        } else {
            return innerSolver.solveBoard(det);
        }
    }

    /**
     * Create a range of integers from start (inclusive) to end (exclusive)
     *
     * @param start The first number in the sequence
     * @param end The end of the sequence (exclusive)
     * @return A list containing the range
     */
    private static List<Integer> range(int start, int end) {
        List<Integer> list = new ArrayList<>(end - start);
        for (int i = start; i < end; i++) {
            list.add(i);
        }
        return list;
    }

    /**
     * Find and set squares that must have a particular value based on known
     * values and Sudoku constraints
     *
     * @param board The board that will be modified
     * @param setFixed If true, make all the known squares we find non-editable
     */
    public static void determineSquares(Board board, boolean setFixed) {
        for (int x = 0; x < Board.BOARD_SIZE; x++) {
            for (int y = 0; y < Board.BOARD_SIZE; y++) {
                final Location loc = new Location(x, y);
                CellValue val = determineSquare(board, loc);
                if (!val.isEmpty() && board.getEditabilityAtLoc(loc)) {
                    board.setValueAtLoc(loc, val);
                    board.setEditabilityAtLoc(loc, false);
                }
            }
        }
    }

    /**
     * Find what value a certain location in the board must have
     *
     * @param board The board
     * @param loc The specific square to check
     * @return
     */
    private static CellValue determineSquare(Board board, Location loc) {
        List<Integer> possibleRow = getPossibleValues(board, loc);
        return possibleRow.size() == 1 ? CellValue.
                createCellValueFromInt(possibleRow.get(0)) : CellValue.EMPTY;
    }

    /**
     * Get possible values at a location on the board
     *
     * @param board The board to use
     * @param loc The location to evaluate
     * @return The possible values that could occur at the location
     */
    public static List<Integer> getPossibleValues(Board board, Location loc) {
        List<Integer> possibleRow = getPossible(board.getRow(loc.getY()));
        List<Integer> possibleCol = getPossible(board.getCol(loc.getX()));
        List<Integer> possibleSq
                = getPossible(board.getSquare(getSquareNum(loc)));
        possibleRow.retainAll(possibleCol);
        possibleRow.retainAll(possibleSq);
        return possibleRow;
    }

    /**
     * Get the possible remaining values in a board segment
     *
     * @param seg The segment
     * @return A list of possible values remaining
     */
    private static List<Integer> getPossible(BoardSegment seg) {
        List<Integer> possible = range(1, Board.BOARD_SIZE + 1);
        for (int i = 0; i < Board.BOARD_SIZE; i++) {
            CellValue val = seg.getValueAtIndex(i);
            if (!val.isEmpty()) {
                possible.remove(new Integer(val.getValue()));
            }
        }
        return possible;
    }

    /**
     * Get the square number based on the location
     *
     * @param loc The location
     * @return The square number
     */
    private static int getSquareNum(Location loc) {
        return loc.getX() / 3 + (loc.getY() / 3) * 3;
    }
}
