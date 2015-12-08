/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Dunni Adenuga
 Tim Woodford
 Andrew Nyhus
 * Date: Dec 6, 2015
 * Time: 3:17:59 PM
 *
 * Project: csci205FinalProject
 * Package: sudoku.solvers.sa
 * File: StatePruner
 * Description:
 *
 * ****************************************
 */
package sudoku.solvers.sa;

import java.util.Arrays;
import java.util.List;
import sudoku.Board;
import sudoku.Location;
import sudoku.Square;
import sudoku.solvers.DeterministicSquareFinder;

/**
 * Used for reducing the number of possible states that tested in SA/CGA
 *
 * @author tww014
 */
public class StatePruner {
    private final List<Integer>[][] possibleValues;

    public StatePruner(Board board) {
        this.possibleValues = new List[Board.BOARD_SIZE][Board.BOARD_SIZE];
        for (int x = 0; x < Board.BOARD_SIZE; x++) {
            for (int y = 0; y < Board.BOARD_SIZE; y++) {
                Location loc = new Location(x, y);
                if (board.getEditabilityAtLoc(loc)) {
                    possibleValues[x][y] = DeterministicSquareFinder.getPossibleValues(
                            board, loc);
                } else {
                    possibleValues[x][y] = Arrays.asList(
                            board.getValueAtLoc(loc).getValue());
                }
            }
        }
    }

    private int[][] connectivityMatrixForSquare(Square sq) {
        // An adjacency matrix mapping square indices -> numbers
        int[][] numMatrix = new int[Board.BOARD_SIZE][Board.BOARD_SIZE];
        for (int i = 0; i < Board.BOARD_SIZE; i++) {
            Location itemLoc = sq.getLocationInSquare(i);
            List<Integer> possible = possibleValues[itemLoc.getX()][itemLoc.getY()];
            for (int integer : possible) {
                numMatrix[i][integer] = 1;
            }
        }
        return mmMultT(numMatrix);
    }

    /**
     * Multiply a matrix by its transpose
     *
     * @param matrix The matrix
     * @return A*A^T
     */
    private static int[][] mmMultT(int[][] matrix) {
        int[][] out = new int[matrix.length][matrix.length];
        for (int r = 0; r < matrix.length; r++) {
            for (int c = 0; c < matrix.length; c++) {
                int sum = 0;
                for (int i = 0; i < matrix[0].length; i++) {
                    sum += matrix[r][i] * matrix[i][c];
                }
                out[r][c] = sum;
            }
        }
        return out;
    }
}
