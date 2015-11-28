/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Dunni Adenuga
 Tim Woodford
 Andrew Nyhus
 * Date: Nov 13, 2015
 * Time: 8:32:18 AM
 *
 * Project: csci205FinalProject
 * Package: sudoku
 * File: BoardSegment
 * Description:
 *
 * ****************************************
 */
package sudoku;

/**
 *
 * @author ajn008
 */
abstract class BoardSegment {

    public abstract CellValue getValueAtIndex(int index);

    public abstract Location getLocationInSquare(int index);

    /**
     * This method determines whether or not a BoardSegment is valid. If the
     * BoardSegment has no duplicates, the BoardSegment is valid.
     *
     * @return a boolean value representing the validity of the BoardSegment
     */
    public boolean isValid() {
        //each int in this array corresponds to a count of occurrences for each number 1-9, based on the index which ranges from 0-8.
        int[] countOfEachValue = {0, 0, 0, 0, 0, 0, 0, 0, 0};

        for (int i = 0; i < Board.BOARD_SIZE; i++) {
            CellValue curr = this.getValueAtIndex(i);

            if (!curr.isEmpty()) {
                countOfEachValue[curr.getValue() - 1]++;
                if (countOfEachValue[curr.getValue() - 1] > 1) {
                    return false;
                }
            }

        }

        return true;
    }

    /**
     * The purpose of this method is to determine if the BoardSegment is
     * completed, by making sure that the board is valid and that a call to
     * BoardSegment.getNumNotPresent is 0.
     *
     * @return a boolean to represent whether or not the BoardSegment is
     * completed.
     */
    public boolean isCompleted() {
        if (!this.isValid()) {
            return false;
        }
        int numNotPresent = this.getNumNotPresent();

        return (numNotPresent == 0);
    }

    /**
     * Get the number of numbers not present in a particular row segment.
     *
     * @return The number of missing numbers (1-9)
     */
    public int getNumNotPresent() {
        //each boolean in this array corresponds to a number 1-9, based on it's index which ranges from 0-8.
        boolean[] indexWasFoundInRow = {false, false, false, false, false, false, false, false, false};
        for (int i = 0; i < Board.BOARD_SIZE; i++) {
            //current CellValue in BoardSegment
            CellValue curr = this.getValueAtIndex(i);
            if (!curr.isEmpty()) {
                indexWasFoundInRow[curr.getValue() - 1] = true;
            }
        }

        int countOfNotPresent = 0;

        for (int i = 0; i < Board.BOARD_SIZE; i++) {
            if (!indexWasFoundInRow[i]) {
                countOfNotPresent++;
            }
        }

        return countOfNotPresent;

    }
}
