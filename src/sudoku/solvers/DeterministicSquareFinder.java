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
 * Sudoku
 *
 * @author Tim Woodford
 */
public class DeterministicSquareFinder {

    private static List<Integer> range(int start, int end) {
        List<Integer> list = new ArrayList<>(end - start);
        for (int i = start; i < end; i++) {
            list.add(i);
        }
        return list;
    }

    public static void determineSquares(Board board) {
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

    public static CellValue determineSquare(Board board, Location loc) {
        List<Integer> possibleRow = getPossible(board.getRow(loc.getY()));
        List<Integer> possibleCol = getPossible(board.getCol(loc.getX()));
        List<Integer> possibleSq
                      = getPossible(board.getSquare(getSquareNum(loc)));
        possibleRow.retainAll(possibleCol);
        possibleRow.retainAll(possibleSq);
        return possibleRow.size() == 1 ? CellValue.
                createCellValueFromInt(possibleRow.get(0)) : CellValue.EMPTY;
    }

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

    private static int getSquareNum(Location loc) {
        return loc.getX() / 3 + (loc.getY() / 3) * 3;
    }
}
