/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Dunni Adenuga
 Tim Woodford
 Andrew Nyhus
 * Date: Nov 13, 2015
 * Time: 09:42:00 AM
 *
 * Project: csci205FinalProject
 * Package: sudoku
 * File: Row
 * Description:
 *
 * ****************************************
 */
package sudoku;

/**
 *
 * @author ajn008
 */
public class Row extends BoardSegment {

    private int rowNum;
    private Board b;

    public Row(int rowNum, Board b) {
        this.rowNum = rowNum;
        this.b = b;
    }

    /**
     * @param index
     * @return CellValue at given index within the Row.
     */
    @Override
    public CellValue getValueAtIndex(int index) {
        return this.b.getValueAtLoc(new Location(index, this.rowNum));

    }

    /**
     * Get the location of a specified index
     *
     * @return Location The location at the index
     */
    @Override
    public Location getLocationInSquare(int index) {
        return new Location(index, this.rowNum);
    }

}
